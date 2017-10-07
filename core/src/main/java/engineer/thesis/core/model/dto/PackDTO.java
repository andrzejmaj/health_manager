package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class PackDTO {

    @XmlAttribute(name = "wielkosc")
    private Float wielkosc;

    @XmlAttribute(name = "jednostkaWielkosci")
    private String jednostkaWielkosci;
}
