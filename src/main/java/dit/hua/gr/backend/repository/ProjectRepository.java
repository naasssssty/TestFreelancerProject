//ProjectRepository.java
package dit.hua.gr.backend.repository;

import dit.hua.gr.backend.model.Project;
import dit.hua.gr.backend.model.ProjectStatus;
import dit.hua.gr.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    // Εύρεση έργων που ανήκουν σε έναν συγκεκριμένο πελάτη
    List<Project> findByClient(User client);

    // Εύρεση έργων με βάση την κατάσταση του έργου
    List<Project> findByProjectStatus(ProjectStatus projectStatus);

    // Εύρεση έργου με βάση τον πελάτη και την κατάσταση
    List<Project> findByClientAndProjectStatus(User client, ProjectStatus projectStatus);

    // Εύρεση έργων που έχουν ολοκληρωθει
    List<Project> findByDeadlineBefore(LocalDate date);

    List<Project> findByTitle(String title);
}
