package engineer.thesis.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String description;

    public Specialization(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Specialization) {
            Specialization s = (Specialization) obj;

            return Objects.equals(description, s.description);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
