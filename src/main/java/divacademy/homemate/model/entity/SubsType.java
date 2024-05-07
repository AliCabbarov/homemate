package divacademy.homemate.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SubsType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(unique = true)
    private String name;
    private String description;
    private double price;
    private int count;
    private boolean available;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TableDetail tableDetail;

    public SubsType(String name, String description, double price, int count, boolean available) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.count = count;
        this.available = available;
        this.tableDetail = TableDetail.of();
    }

    public SubsType() {
        tableDetail = TableDetail.of();
    }
}
