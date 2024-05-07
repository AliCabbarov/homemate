package divacademy.homemate.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AdvertType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String description;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TableDetail tableDetail;


    public AdvertType(String name,String description) {
        this.name = name;
        this.description = description;
        this.tableDetail = TableDetail.of();
    }

    public AdvertType() {
        this.tableDetail = TableDetail.of();
    }
}
