package dit.hua.gr.backend.controller;

import dit.hua.gr.backend.model.*;
import dit.hua.gr.backend.service.ApplicationService;
import dit.hua.gr.backend.service.HireService;
import dit.hua.gr.backend.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hires")
public class HireController {

    private final HireService hireService;
    private final ApplicationService applicationService;
    private final ProjectService projectService;

    public HireController(HireService hireService, ApplicationService applicationService, ProjectService projectService) {
        this.hireService = hireService;
        this.applicationService = applicationService;
        this.projectService = projectService;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/applications/{id}")
    public ResponseEntity<Hire> appproveApplication(@PathVariable Integer id){
        Hire hire = hireService.approveApplication(id);
        applicationService.updateApplicationStatus(id, ApplicationStatus.APPROVED);

        return ResponseEntity.ok(hire);
    }

    @PreAuthorize("hasRole('FREELANCER')")
    @PutMapping("/hire/{id}/complete")
    public ResponseEntity<Hire> completeProject(@PathVariable Integer id){
          return ResponseEntity.ok(hireService.completeProject(id));
    }
}
