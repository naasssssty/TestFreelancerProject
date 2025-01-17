package dit.hua.gr.backend.controller;

import dit.hua.gr.backend.model.Application;
import dit.hua.gr.backend.model.ApplicationStatus;
import dit.hua.gr.backend.model.Project;
import dit.hua.gr.backend.model.User;
import dit.hua.gr.backend.service.ApplicationService;
import dit.hua.gr.backend.service.ProjectService;
import dit.hua.gr.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ProjectService projectService;
    private final UserService userService;

    public ApplicationController(ApplicationService applicationService, ProjectService projectService, UserService userService) {
        this.applicationService = applicationService;
        this.projectService = projectService;
        this.userService = userService;
    }

    // Δημιουργία νέας αίτησης (μόνο για FREELANCER)
//    @PreAuthorize("hasRole('FREELANCER')")
//    @PostMapping("/{freelancer_username}")
//    public ResponseEntity<Application> applyForProject(@RequestBody Integer id, @PathVariable String freelancer_username) {
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
    @PostMapping("/projects/{projectId}/apply/{username}")
    @PreAuthorize("hasRole('FREELANCER')")
    public ResponseEntity<Application> applyForProject(@PathVariable Integer projectId, @PathVariable String username, @RequestBody String cover_letter) {
        Application application = applicationService.createApplication(projectId, username, cover_letter);
        return ResponseEntity.ok(application);
    }

    // Εύρεση αιτήσεων από έναν freelancer (μόνο για τον FREELANCER ή ADMIN)
    @PreAuthorize("hasRole('FREELANCER') or hasRole('ADMIN')")
    @GetMapping("/freelancer/{freelancerId}")
    public ResponseEntity<List<Application>> getApplicationsByFreelancer(@PathVariable Integer freelancerId) {
        User freelancer = new User();
        freelancer.setId(freelancerId);
        List<Application> applications = applicationService.getApplicationsByFreelancer(freelancer);
        return ResponseEntity.ok(applications);
    }

    // Εύρεση αιτήσεων για συγκεκριμένο project (μόνο για CLIENT ή ADMIN)
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Application>> getApplicationsByProject(@PathVariable Integer projectId) {
        Project project = new Project();
        project.setId(projectId);
        List<Application> applications = applicationService.getApplicationsByProject(project);
        return ResponseEntity.ok(applications);
    }

    // Εύρεση αιτήσεων με συγκεκριμένη κατάσταση (μόνο για ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Application>> getApplicationsByStatus(@PathVariable String status) {
        ApplicationStatus applicationStatus = ApplicationStatus.valueOf(status.toUpperCase());
        List<Application> applications = applicationService.getApplicationsByStatus(applicationStatus);
        return ResponseEntity.ok(applications);
    }

    // Εύρεση αίτησης με βάση το project και τον freelancer (μόνο για ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/project/{projectId}/freelancer/{freelancerId}")
    public ResponseEntity<Application> getApplicationByProjectAndFreelancer(
            @PathVariable Integer projectId, @PathVariable Integer freelancerId) {

        Project project = new Project();
        project.setId(projectId);

        User freelancer = new User();
        freelancer.setId(freelancerId);

        Optional<Application> application = applicationService.getApplicationByProjectAndFreelancer(project, freelancer);

        return application.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Ενημέρωση κατάστασης αίτησης (μόνο για ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Integer applicationId, @RequestBody ApplicationStatus status) {
        Application updatedApplication = applicationService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok(updatedApplication);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/applications/{id}/deny")
    public ResponseEntity<Application> rejectApplication(@PathVariable Integer id){
        return ResponseEntity.ok(applicationService.updateApplicationStatus(id, ApplicationStatus.REJECTED));
    }



    // Διαγραφή αίτησης (μόνο για ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Integer applicationId) {
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.noContent().build();
    }
}

