package engineer.thesis.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"timeSlot"})
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@OneToOne(optional = false)
	@JoinColumn(name = "time_slot_id")
	private TimeSlot timeSlot;

	@Column(nullable = true)
	private Integer officeNumber;
	
	@Column(nullable = false)
	private boolean tookPlace;
	
	@Column(nullable = true)
	private String data;
	
	public Appointment(Patient patient, TimeSlot timeSlot, Integer officeNumber, boolean tookPlace, String data) {
		this.patient = patient;
		this.timeSlot = timeSlot;
		this.officeNumber = officeNumber;
		this.tookPlace = tookPlace;
		this.data = data;
	}
}
