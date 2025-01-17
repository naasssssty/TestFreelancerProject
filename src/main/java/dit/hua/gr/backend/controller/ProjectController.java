package dit.hua.gr.backend.controller;

import dit.hua.gr.backend.dto.PostProjectDTO;
import dit.hua.gr.backend.model.Project;
import dit.hua.gr.backend.model.User;
import dit.hua.gr.backend.service.ProjectService;
import dit.hua.gr.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

//    @PreAuthorize("hasRole('FREELANCER')")
//    @GetMapping("/{id}/apply/{freelancer_username}")
//
//    public ResponseEntity<Application> applyForProject(@PathVariable Integer id, @PathVariable String freelancer_username) {
//        Optional<Project> project = projectService.findProjectById(id);
//        if (project.isPresent()) {
//            Optional<User> freelancer = userService.findUserByUsername(freelancer_username);
//            Application application = new Application();
//            application.setProject(project.get());
//            application.setFreelancer(freelancer.get());
//            application.setApplicationStatus(ApplicationStatus.WAITING);
//            return ResponseEntity.ok(application);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    // Δημιουργία νέου έργου (μόνο για CLIENT)
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("{client_username}/post")
    public ResponseEntity<Project> postProject(@PathVariable String client_username, @RequestBody PostProjectDTO projectDTO) {
        Project newProject = new Project();
        Optional<User> client = userService.findUserByUsername(client_username);
        newProject.setClient(client.get());
        newProject.setTitle(projectDTO.getTitle());
        newProject.setDescription(projectDTO.getDescription());
        newProject.setBudget(projectDTO.getBudget());
        newProject.setDeadline(projectDTO.getDeadline());
        Project savedProject = projectService.saveProject(newProject);
        return ResponseEntity.ok(savedProject);
    }

    // Εύρεση έργου με βάση το ID (για ADMIN, CLIENT και FREELANCER αν είναι ανατεθειμένο σε αυτούς)
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT') or hasRole('FREELANCER')")
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Integer id) {
        Optional<Project> project = projectService.findProjectById(id);
        return project.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('FREELANCER')")
    @GetMapping("/freelancer/{freelancer_username}")
    public ResponseEntity<List<Project>> getProjectsByTitle(@PathVariable String title) {
        List<Project> projects = projectService.findProjectsByTitle(title);
        return ResponseEntity.ok(projects);
    }

    // Εύρεση όλων των έργων (μόνο για ADMIN)
    @GetMapping("/allProjects")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.findAllProjects());
    }

    // Εύρεση διαθέσιμων έργων
    @GetMapping("/projects")
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<List<Project>> getAvailableProjects() {
        List<Project> projects = projectService.findAvailableProjects();
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/projects/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> approveProject(@PathVariable Integer id) {
        Project project = projectService.approveProject(id);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/projects/{id}/deny")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> rejectProject(@PathVariable Integer id){
        Project project = projectService.rejectProject(id);
        return ResponseEntity.ok(project);
    }

    // Ενημέρωση έργου (μόνο για ADMIN)
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer id, @RequestBody Project updatedProject) {
        try {
            Project project = projectService.updateProject(id, updatedProject);
            return ResponseEntity.ok(project);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Διαγραφή έργου (μόνο για ADMIN)
    @DeleteMapping("/projects/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Client can see the projects that are assigned to him
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    @GetMapping("/client/{client_username}")
    public ResponseEntity<List<Project>> getProjectsByClient(@PathVariable String client_username) {;
        List<Project> projects = projectService.findProjectsByClient(client_username);
        return ResponseEntity.ok(projects);
    }


}
