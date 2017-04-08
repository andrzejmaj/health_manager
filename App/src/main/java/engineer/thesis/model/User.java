package engineer.thesis.model;

import lombok.Data;

import javax.persistence.*;
import java.nio.file.attribute.UserDefinedFileAttributeView;

/**
 * Created by Kamil on 2017-04-07.
 */

@Entity
@Data
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private UserRole role;



}
