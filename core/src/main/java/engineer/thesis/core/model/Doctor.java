package engineer.thesis.core.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Specialization> specializations;

	public Doctor(User user, Set<Specialization> specializations) {
		this.user = user;
		this.specializations = specializations;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj instanceof Doctor) {
            Doctor d = (Doctor) obj;

            return Objects.equals(d.user, this.user);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}

