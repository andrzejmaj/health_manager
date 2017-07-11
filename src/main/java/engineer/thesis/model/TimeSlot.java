package engineer.thesis.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TimeSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private LocalDateTime startDateTime;
	
	@Column(nullable = false)
	private LocalDateTime endDateTime;
	
	@ManyToOne(optional = false)
	private Doctor doctor;

	public TimeSlot(LocalDateTime startDateTime, LocalDateTime endDateTime, Doctor doctor) {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.doctor = doctor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TimeSlot) {
			TimeSlot t = (TimeSlot) obj;
			
			return Objects.equals(startDateTime, t.startDateTime) &&
					Objects.equals(endDateTime, t.endDateTime) &&
					Objects.equals(doctor, t.doctor);
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(startDateTime, endDateTime, doctor);
	}
}

