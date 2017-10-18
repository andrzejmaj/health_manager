package engineer.thesis.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_time_slot", schema = "hmanager")
public class TimeSlot {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(nullable = false)
	private Date startDateTime;
	
	@Column(nullable = false)
	private Date endDateTime;
	
	@ManyToOne(optional = false)
	private Doctor doctor;

	@Column(nullable = false)
	private boolean availableForSelfSign;

}

