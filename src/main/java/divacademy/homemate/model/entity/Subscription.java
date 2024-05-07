package divacademy.homemate.model.entity;

import divacademy.homemate.model.dto.response.ExceptionResponse;
import divacademy.homemate.model.enums.Exceptions;
import divacademy.homemate.model.exception.ApplicationException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private double amount;
    private int count;
    private boolean available;
    private boolean confirm;
    @ManyToOne
    private SubsType subsType;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<Advert> adverts;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TableDetail tableDetail;

    public Subscription(User user, SubsType subsType) {
        this.subsType = subsType;
        this.user = user;
        this.description = subsType.getDescription();
        this.amount = subsType.getPrice();
        this.count = subsType.getCount();
        tableDetail = TableDetail.of();
        this.available = true;
        this.confirm = false;
    }

    public void addAdvert(Advert advert) {
        this.adverts.add(advert);
        this.count-=1;

        if (count == 0) {
            available = false;
        }
    }

    public boolean isItInSubs(Advert advert) {
        return adverts.stream()
                .anyMatch(adv -> adv.getId() == advert.getId());
    }

    public void available() {
        this.available = true;
    }
}
