package divacademy.homemate.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Permission> permissions;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TableDetail tableDetail;

    public Role(String name, List<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
        this.tableDetail = TableDetail.of();
    }
}
