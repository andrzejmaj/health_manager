package engineer.thesis.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Specialization {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
