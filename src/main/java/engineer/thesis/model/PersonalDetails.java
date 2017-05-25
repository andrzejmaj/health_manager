package engineer.thesis.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "hm_personal_detail", schema = "hmanager")
public class PersonalDetails {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "pesel", unique = true, nullable = false)
    private String pesel;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "country")
    private String country;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "building_number")
    private int buildingNumber;

    @Column(name = "flat_number")
    private int flatNumber;

}
