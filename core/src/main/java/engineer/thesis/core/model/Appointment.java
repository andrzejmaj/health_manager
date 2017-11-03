package engineer.thesis.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @Column(nullable = true)
    private Integer officeNumber;

    @Column(nullable = false)
    private boolean tookPlace;

    @Column(nullable = true)
    private String data;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private VisitPriority priority;

    public Appointment(Patient patient, TimeSlot timeSlot, Integer officeNumber, boolean tookPlace, String data, VisitPriority priority) {
        this.patient = patient;
        this.timeSlot = timeSlot;
        this.officeNumber = officeNumber;
        this.tookPlace = tookPlace;
        this.data = data;
        this.priority = priority;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Appointment) {
            Appointment a = (Appointment) obj;

            return Objects.equals(a.timeSlot, this.timeSlot);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSlot);
    }
}
