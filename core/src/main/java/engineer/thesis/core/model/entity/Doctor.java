package engineer.thesis.core.model.entity;

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
@Table(name = "hm_doctor", schema = "hmanager")
public class Doctor {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Account account;


    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;


    public Doctor(Account account, Specialization specialization) {
        this.account = account;
        this.specialization = specialization;
    }
}

