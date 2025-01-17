package dit.hua.gr.backend.dto;

import java.time.LocalDate;

public class PostProjectDTO {

    private String title;
    private String description;
    private Double budget;
    private LocalDate deadline;

    public PostProjectDTO() {
    }

    public PostProjectDTO(String title, String description, Double budget, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
