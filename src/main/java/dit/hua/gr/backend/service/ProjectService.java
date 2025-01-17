package dit.hua.gr.backend.service;

import dit.hua.gr.backend.model.Project;
import dit.hua.gr.backend.model.ProjectStatus;
import dit.hua.gr.backend.model.User;
import dit.hua.gr.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    public Project approveProject(Integer id){
        return projectRepository.findById(id).map(existingProject ->{
            existingProject.setProjectStatus(ProjectStatus.APPROVED);
            return projectRepository.save(existingProject);
        }).orElseThrow(() -> new IllegalArgumentException("Project with id " + id + " not found"));
    }

    public Project rejectProject(Integer id){
        return projectRepository.findById(id).map(existingProject ->{
            existingProject.setProjectStatus(ProjectStatus.DENIED);
            return projectRepository.save(existingProject);
        }).orElseThrow(() -> new IllegalArgumentException("Project with id " + id + " not found"));
    }

    public List<Project> findAvailableProjects() {
        return projectRepository.findByProjectStatus(ProjectStatus.APPROVED);
    }

    // Δημιουργία ή αποθήκευση ενός έργου
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    // Εύρεση έργου με βάση το ID
    public Optional<Project> findProjectById(Integer id) {
        return projectRepository.findById(id);
    }

    // Εύρεση όλων των έργων
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }


    public List<Project> findProjectsByTitle(String title) {
        return projectRepository.findByTitle(title);
    }

    public List<Project> findProjectsByClient(String username) {
        User client = userService.findUserByUsername(username).orElseThrow(() -> new IllegalArgumentException("User with username " + username + " not found"));
        return projectRepository.findByClient(client);
    }

    // Ενημέρωση έργου
    public Project updateProject(Integer id, Project updatedProject) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setTitle(updatedProject.getTitle());
                    project.setDescription(updatedProject.getDescription());
                    project.setBudget(updatedProject.getBudget());
                    project.setDeadline(updatedProject.getDeadline());
                    project.setClient(updatedProject.getClient());
                    return projectRepository.save(project);
                })
                .orElseThrow(() -> new IllegalArgumentException("Project with id " + id + " not found"));
    }

    // Διαγραφή έργου
    public void deleteProject(Integer id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Project with id " + id + " not found");
        }
    }
}
