package engineer.thesis.core.model;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
//    @JoinColumn(name = "specialisation_id", nullable = false, updatable = false)
    private long id;
	
	@Column(nullable = false)
	private String description;

	public Specialization(String name) {
		this.description = name;
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
