package divacademy.homemate.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AdvertDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate moveTime;
    private int livingCount;
    private int searchingCount;
    @ManyToOne
    private Gender gender;

    public AdvertDetail(LocalDate moveTime, int livingCount, int searchingCount, Gender gender) {
        this.moveTime = moveTime;
        this.livingCount = livingCount;
        this.searchingCount = searchingCount;
        this.gender = gender;
    }

    public AdvertDetail(Gender gender) {
        this.gender = gender;
    }
}
