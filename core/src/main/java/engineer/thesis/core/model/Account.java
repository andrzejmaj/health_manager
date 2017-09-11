package engineer.thesis.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_account", schema = "hmanager")
public class Account {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @Cascade(CascadeType.ALL)
    private User user;

    @OneToOne
    @JoinColumn(name = "personal_details_id")
    @Cascade(CascadeType.ALL)
    private PersonalDetails personalDetails;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_date")
    private Date createdDate;

}
