package divacademy.homemate.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PropertyType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "property_type_seq")
    @SequenceGenerator(name = "property_type_seq", initialValue = 150,sequenceName = "property_type_seq",allocationSize = 50)
    private long id;
    private String name;
    private String description;
    private boolean available;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TableDetail tableDetail;

    public PropertyType(String name, String description, boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.tableDetail = TableDetail.of();
    }

    public PropertyType() {
        this.tableDetail = TableDetail.of();

    }
}
