//package dit.hua.gr.backend.controller;
//
//import dit.hua.gr.backend.dto.PostProjectDTO;
//import dit.hua.gr.backend.model.*;
//import dit.hua.gr.backend.service.ApplicationService;
//import dit.hua.gr.backend.service.HireService;
//import dit.hua.gr.backend.service.ProjectService;
//import dit.hua.gr.backend.service.UserService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/client")
//public class ClientController {
//
//    private final ApplicationService applicationService;
//    private final ProjectService projectService;
//    private final UserService userService;
//    private final HireService hireService;
//
//    public ClientController(ApplicationService applicationService, ProjectService projectService, UserService userService, HireService hireService) {
//        this.applicationService = applicationService;
//        this.projectService = projectService;
//        this.userService = userService;
//        this.hireService = hireService;
//    }
//
//
//    @PreAuthorize("hasRole('CLIENT')")
//    @PostMapping("{client_username}/post")
//    public ResponseEntity<Project> postProject(@PathVariable String client_username, @RequestBody PostProjectDTO projectDTO) {
//        Project newProject = new Project();
//        Optional<User> client = userService.findUserByUsername(client_username);
//        newProject.setClient(client.get());
//        newProject.setTitle(projectDTO.getTitle());
//        newProject.setDescription(projectDTO.getDescription());
//        newProject.setBudget(projectDTO.getBudget());
//        newProject.setDeadline(projectDTO.getDeadline());
//        Project savedProject = projectService.saveProject(newProject);
//        return ResponseEntity.ok(savedProject);
//    }
//
//    @PreAuthorize("hasRole('CLIENT')")
//    @PostMapping("/applications/{id}")
//    public ResponseEntity<Hire> appproveApplication(@PathVariable Integer id){
//        Hire hire = hireService.approveApplication(id);
//        return ResponseEntity.ok(hire);
//    }
//}
