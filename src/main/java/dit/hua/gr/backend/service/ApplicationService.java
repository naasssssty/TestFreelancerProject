package dit.hua.gr.backend.service;

import dit.hua.gr.backend.model.*;
import dit.hua.gr.backend.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, UserService userService, ProjectService projectService) {
        this.applicationRepository = applicationRepository;
        this.userService = userService;
        this.projectService = projectService;
    }

    // Δημιουργία νέας αίτησης
    public Application createApplication(Integer projectId, String freelancerUsername, String cover_letter) {
        Optional<Project> projectOpt = projectService.findProjectById(projectId);
        Project project = projectOpt.orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));
        Optional<User> freelancerOpt = userService.findUserByUsername(freelancerUsername);
        User freelancer = freelancerOpt.orElseThrow(() -> new RuntimeException("Freelancer not found with username: " + freelancerUsername));
        Application application = new Application();
        application.setProject(project);
        application.setFreelancer(freelancer);
        application.setApplicationStatus(ApplicationStatus.WAITING);
        application.setCover_letter(cover_letter);
        return applicationRepository.save(application);
    }

    // Εύρεση αιτήσεων που έχει υποβάλει συγκεκριμένος freelancer
    public List<Application> getApplicationsByFreelancer(User freelancer) {
        return applicationRepository.findByFreelancer(freelancer);
    }

    // Εύρεση αιτήσεων για συγκεκριμένο project
    public List<Application> getApplicationsByProject(Project project) {
        return applicationRepository.findByProject(project);
    }

    // Εύρεση αιτήσεων με συγκεκριμένη κατάσταση
    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByApplicationStatus(status);
    }

    // Εύρεση αίτησης με βάση το project και τον freelancer
    public Optional<Application> getApplicationByProjectAndFreelancer(Project project, User freelancer) {
        return applicationRepository.findByProjectAndFreelancer(project, freelancer);
    }

    // Ενημέρωση κατάστασης αίτησης
    public Application updateApplicationStatus(Integer applicationId, ApplicationStatus status) {
        Optional<Application> applicationOpt = applicationRepository.findById(applicationId);
        Application application = applicationOpt.orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
        application.setApplicationStatus(status);
        return applicationRepository.save(application);
    }

    // Διαγραφή αίτησης
    public void deleteApplication(Integer applicationId) {
        if (applicationRepository.existsById(applicationId)) {
            applicationRepository.deleteById(applicationId);
        } else {
            throw new RuntimeException("Application not found with ID: " + applicationId);
        }
    }
}
