package engineer.thesis.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
@Entity
@Table(name = "hm_patient")
public class Patient {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "insurance_number", unique = true, nullable = false)
    private String insuranceNumber;

    @OneToOne
    @JoinColumn(name = "personal_details", unique = true, nullable = false)
    private PersonalDetails personalDetails;

    @OneToOne
    @JoinColumn(name = "emergency_contact")
    private PersonalDetails emergencyContact;





}
