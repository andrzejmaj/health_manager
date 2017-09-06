package engineer.thesis.core.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"startDateTime", "endDateTime", "doctor"})
@NoArgsConstructor
public class TimeSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private Date startDateTime;
	
	@Column(nullable = false)
	private Date endDateTime;
	
	@ManyToOne(optional = false)
	private Doctor doctor;

	@Column(nullable = false)
	private boolean availableForSelfSign;

	public TimeSlot(Date startDateTime, Date endDateTime, Doctor doctor, boolean availableForSelfSign) {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.doctor = doctor;
		this.availableForSelfSign = availableForSelfSign;
	}
}

