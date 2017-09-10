package engineer.thesis.core.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

