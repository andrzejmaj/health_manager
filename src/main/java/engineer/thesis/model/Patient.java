package engineer.thesis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_patient" , schema = "hmanager")
public class Patient {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "insurance_number", nullable = false)
    private String insuranceNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_details_id", nullable = false)
    private PersonalDetails personalDetails;

    @OneToOne
    @JoinColumn(name = "emergency_contact_id")
    private PersonalDetails emergencyContact;


}
