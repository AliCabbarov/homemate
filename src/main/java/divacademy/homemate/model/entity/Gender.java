package divacademy.homemate.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.IdentityHashMap;

@Entity
@Getter
@Setter
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private boolean available;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TableDetail tableDetail;

    public Gender(String name, boolean available) {
        this.name = name;
        this.available = available;
        this.tableDetail = TableDetail.of();
    }

    public Gender() {
        tableDetail = TableDetail.of();
    }
}
