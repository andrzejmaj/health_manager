package engineer.thesis.core.security.model;

import engineer.thesis.core.model.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "hm_reset_token", schema = "hmanager")
@NoArgsConstructor
public class PasswordResetToken {

    private static final long HOUR = 3600 * 1000;

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = new Date(System.currentTimeMillis() + 3 * HOUR);
        this.isActive = true;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiration_date", nullable = false)
    private Date expiryDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}