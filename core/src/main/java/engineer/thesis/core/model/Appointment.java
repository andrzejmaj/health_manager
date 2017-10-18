package engineer.thesis.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_appointment", schema = "hmanager")
public class Appointment {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@OneToOne(optional = false)
	@JoinColumn(name = "time_slot_id")
	private TimeSlot timeSlot;

	@Column(name = "office_number")
	private Integer officeNumber;
	
	@Column(nullable = false)
	private boolean tookPlace;

	@Column(nullable = false)
    private String priority = "low";

    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<AppointmentComment> comments;

}
