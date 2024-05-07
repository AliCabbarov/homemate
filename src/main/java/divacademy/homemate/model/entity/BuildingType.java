package divacademy.homemate.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BuildingType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String description;
    private boolean available;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TableDetail tableDetail;

    public BuildingType(String name, String description, boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.tableDetail = TableDetail.of();
    }

    public BuildingType() {
        this.tableDetail = TableDetail.of();
    }
}
