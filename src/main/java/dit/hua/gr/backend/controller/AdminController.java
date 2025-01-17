//package dit.hua.gr.backend.controller;
//
//import dit.hua.gr.backend.model.Project;
//import dit.hua.gr.backend.model.User;
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
//@RequestMapping("/admin")
//public class AdminController {
//
//    private final ApplicationService applicationService;
//    private final ProjectService projectService;
//    private final UserService userService;
//
//    public AdminController(ApplicationService applicationService, ProjectService projectService, UserService userService) {
//        this.applicationService = applicationService;
//        this.projectService = projectService;
//        this.userService = userService;
//    }
//
//    @GetMapping("/projects")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<Project>> getAllProjects() {
//        return ResponseEntity.ok(projectService.findAllProjects());
//    }
//
//    @PutMapping("/freelancer/{username}/verify")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<User> verifyFreelancer(@PathVariable String username, @RequestBody Boolean verify){
//        return ResponseEntity.ok(userService.verifyFreelancer(username, verify));
//    }
//
//    @PutMapping("/projects/{id}/approve")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Project> approveProject(@PathVariable Integer id) {
//        Project project = projectService.approveProject(id);
//        return ResponseEntity.ok(project);
//    }
//
//    @PutMapping("/projects/{id}/deny")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Project> rejectProject(@PathVariable Integer id){
//        Project project = projectService.rejectProject(id);
//        return ResponseEntity.ok(project);
//    }
//
//    @DeleteMapping("/projects/{id}")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
//    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
//        try {
//            projectService.deleteProject(id);
//            return ResponseEntity.noContent().build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
