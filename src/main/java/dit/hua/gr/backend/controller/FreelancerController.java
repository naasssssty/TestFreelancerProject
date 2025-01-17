//package dit.hua.gr.backend.controller;
//
//
//import dit.hua.gr.backend.dto.ApplicationDTO;
//import dit.hua.gr.backend.model.Application;
//import dit.hua.gr.backend.model.Project;
//import dit.hua.gr.backend.service.ApplicationService;
//import dit.hua.gr.backend.service.ProjectService;
//import dit.hua.gr.backend.service.UserService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/freelancer")
//public class FreelancerController {
//
//    private final ApplicationService applicationService;
//    private final ProjectService projectService;
//    private final UserService userService;
//
//    public FreelancerController(ApplicationService applicationService, ProjectService projectService, UserService userService) {
//        this.applicationService = applicationService;
//        this.projectService = projectService;
//        this.userService = userService;
//    }
//
//    // Εύρεση διαθέσιμων έργων
//    @GetMapping("/projects")
//    @PreAuthorize("hasRole('FREELANCER')")
//    public ResponseEntity<List<Project>> getAvailableProjects() {
//        List<Project> projects = projectService.findAvailableProjects();
//        return ResponseEntity.ok(projects);
//    }
//
//    @PostMapping("/projects/{projectId}/apply/{username}")
//    @PreAuthorize("hasRole('FREELANCER')")
//    public ResponseEntity<Application> applyForProject(@PathVariable Integer projectId, @PathVariable String username, @RequestBody String cover_letter) {
//        Application application = applicationService.createApplication(projectId, username, cover_letter);
//        return ResponseEntity.ok(application);
//    }
//
//
//}
