package divacademy.homemate.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter
@Entity
@NoArgsConstructor
@Setter
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private double amount;
    private boolean active;
    private long expiredDay;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AdvertType> advertTypes;
    @OneToOne(cascade = CascadeType.PERSIST)
    private AdvertDetail advertDetail;
    @OneToOne(cascade = CascadeType.PERSIST)
    private PropertyDetail propertyDetail;
    @ManyToOne
    private User user;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TableDetail tableDetail;


    public Advert(List<AdvertType> advertTypes, AdvertDetail advertDetail, PropertyDetail propertyDetail,User user){
        this.tableDetail = TableDetail.of();
        this.active = true;
        this.advertTypes = advertTypes;
        this.advertDetail = advertDetail;
        this.propertyDetail = propertyDetail;
        this.user = user;
        this.expiredDay = 10;
    }

    @Scheduled(fixedDelay = 1,timeUnit = TimeUnit.DAYS)
    public void updateStatusByExpired() {
        if (tableDetail.getUpdatedAt().getDayOfYear() + expiredDay > LocalDateTime.now().getDayOfYear()) {
            this.active = false;
        }
    }
}
