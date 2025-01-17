package dit.hua.gr.backend.repository;

import dit.hua.gr.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HireRepository extends JpaRepository<Hire, Integer> {

    Optional<Hire> findByApplication(Application application);
    // Εύρεση προσλήψεων με συγκεκριμένη κατάσταση (π.χ., PENDING, APPROVED)
    List<Hire> findByStatus(HireStatus status);

    Optional<Hire> findById(Integer id);

}
