//Hire.java
package dit.hua.gr.backend.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "hires")
public class Hire {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "hire_date")
    private LocalDateTime hireDate = LocalDateTime.now();

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private HireStatus status;

    @OneToOne
    @JoinTable(name = "application_id")
    private Application application;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
    }

    public HireStatus getStatus() {
        return status;
    }

    public void setStatus(HireStatus status) {
        this.status = status;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

}
