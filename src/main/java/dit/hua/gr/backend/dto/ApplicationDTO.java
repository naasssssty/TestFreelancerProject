package dit.hua.gr.backend.dto;

import dit.hua.gr.backend.model.ApplicationStatus;
import dit.hua.gr.backend.model.Project;
import dit.hua.gr.backend.model.User;

public class ApplicationDTO {
    private Project project;
    private String cover_letter;
    private ApplicationStatus applicationStatus;
    private User freelancer;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getCover_letter() {
        return cover_letter;
    }

    public void setCover_letter(String cover_letter) {
        this.cover_letter = cover_letter;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getFreelancer() {
        return freelancer.getUsername();
    }
}
