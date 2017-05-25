package engineer.thesis.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "hm_patient" , schema = "hmanager")
public class Patient {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "insurance_number", nullable = false)
    private String insuranceNumber;

    @OneToOne
    @JoinColumn(name = "personal_details_id", nullable = false)
    private PersonalDetails personalDetails;

    @OneToOne
    @JoinColumn(name = "emergency_contact_id")
    private PersonalDetails emergencyContact;


}
