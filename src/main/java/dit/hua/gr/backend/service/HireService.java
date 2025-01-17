package dit.hua.gr.backend.service;

import dit.hua.gr.backend.model.*;
import dit.hua.gr.backend.repository.ApplicationRepository;
import dit.hua.gr.backend.repository.HireRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class HireService {

    private final HireRepository hireRepo;
    private final UserService userService;
    private final ApplicationRepository applicationRepo;

    @Autowired
    public HireService(HireRepository hireRepo, UserService userService, ApplicationRepository applicationRepo) {
        this.hireRepo = hireRepo;
        this.userService = userService;
        this.applicationRepo = applicationRepo;
    }

    public Hire approveApplication(Integer applicationId) {
        Optional<Application> applicationOpt = applicationRepo.findById(applicationId);
        Application application = applicationOpt.orElseThrow(() -> new IllegalArgumentException("Application with id " + applicationId + " not found"));
        application.setApplicationStatus(ApplicationStatus.APPROVED);
        Hire hire = new Hire();
        hire.setApplication(application);
        hire.setHireDate(LocalDateTime.now());
        hire.setStatus(HireStatus.PENDING);
        return hireRepo.save(hire);
    }

    public Hire completeProject(Integer id){
       return hireRepo.findById(id).map(existingHire -> {
            existingHire.setStatus(HireStatus.COMPLETED);
            existingHire.getApplication().getProject().setProjectStatus(ProjectStatus.COMPLETED);
            return hireRepo.save(existingHire);
        }).orElseThrow(() -> new IllegalArgumentException("Project with id " + id + " not found"));
    }

    public Hire getById(Integer id){
        return hireRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Hire not found"));
    }
}
