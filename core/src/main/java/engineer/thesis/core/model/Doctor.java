package engineer.thesis.core.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "account")
@NoArgsConstructor
public class Doctor {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "doctor_id", nullable = false, updatable = false)
    private long id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Account account;


    private String specialisation;


    //TODO jak zrobić te specjalizacje :/
//	@ManyToMany(fetch = FetchType.LAZY)
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    @JoinTable(name = "doctor_specialisation", joinColumns = {
//            @JoinColumn(name = "doctor_id", nullable = false, updatable = false) },
//            inverseJoinColumns = { @JoinColumn(name = "specialisation_id",
//                    nullable = false, updatable = false) })
//    private Set<Specialization> specializations;

    public Doctor(Account account, String specialisation /*Set<Specialization> specializations*/) {
        this.account = account;
        this.specialisation = specialisation;
//        this.specializations = specializations;
    }
}

