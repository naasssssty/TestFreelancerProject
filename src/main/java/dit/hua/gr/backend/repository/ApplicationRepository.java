package dit.hua.gr.backend.repository;

import dit.hua.gr.backend.model.Application;
import dit.hua.gr.backend.model.User;
import dit.hua.gr.backend.model.Project;
import dit.hua.gr.backend.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    // Εύρεση αιτήσεων που έχει υποβάλει συγκεκριμένος freelancer
    List<Application> findByFreelancer(User freelancer);

    // Εύρεση αιτήσεων για συγκεκριμένο project
    List<Application> findByProject(Project project);

    // Εύρεση αιτήσεων που έχουν συγκεκριμένη κατάσταση
    List<Application> findByApplicationStatus(ApplicationStatus applicationStatus);

    // Εύρεση αίτησης με βάση το project και το freelancer
    Optional<Application> findByProjectAndFreelancer(Project project, User freelancer);

    Optional<Application> findById(Integer id);
}
