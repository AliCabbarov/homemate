package divacademy.homemate.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
public class UserDetail {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    @OneToOne()
    private User user;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TableDetail tableDetail;

    public UserDetail(String firstname, String lastname, LocalDate birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.tableDetail = TableDetail.of();
    }

    public UserDetail() {
        this.tableDetail = TableDetail.of();
    }
}
