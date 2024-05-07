package divacademy.homemate.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class PropertyDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    private int floorCount;
    private int floor;
    private boolean gas;
    private boolean elevator;
    @ManyToOne
    private BuildingType buildingType;
    @ManyToMany
    private List<PropertyType> propertyTypes;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TableDetail tableDetail;

    public PropertyDetail(String address, int floorCount, int floor, boolean gas, boolean elevator, BuildingType buildingType, List<PropertyType> propertyTypes) {
        this.address = address;
        this.floorCount = floorCount;
        this.floor = floor;
        this.gas = gas;
        this.elevator = elevator;
        this.buildingType = buildingType;
        this.propertyTypes = propertyTypes;
        this.tableDetail = TableDetail.of();
    }

    public PropertyDetail(BuildingType buildingType, List<PropertyType> propertyTypes) {
        this.buildingType = buildingType;
        this.propertyTypes = propertyTypes;
        this.tableDetail = TableDetail.of();
    }
}
