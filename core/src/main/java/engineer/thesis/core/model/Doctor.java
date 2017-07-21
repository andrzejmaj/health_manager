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
    private long id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Account account;


    private String specialisation;


    public Doctor(Account account, String specialisation) {
        this.account = account;
        this.specialisation = specialisation;
    }
}

