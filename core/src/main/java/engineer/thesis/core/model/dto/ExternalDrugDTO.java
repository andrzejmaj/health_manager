package engineer.thesis.core.model.dto;

import engineer.thesis.core.batch.model.DrugType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "produktLeczniczy", namespace = "http://rejestrymedyczne.csioz.gov.pl/rpl/eksport-danych-v1.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExternalDrugDTO {

    @XmlAttribute(name = "rodzajPreparatu")
    private DrugType rodzajPreparatu;

    @XmlAttribute(name = "nazwaProduktu")
    private String nazwaProduktu;

    @XmlAttribute(name = "nazwaPowszechnieStosowana")
    private String nazwaPowszechnieStosowana;

    @XmlAttribute(name = "moc")
    private String moc;

    @XmlAttribute(name = "waznoscPozwolenia")
    private String waznoscPozwolenia;

    @XmlAttribute(name = "podmiotOdpowiedzielny")
    private String podmiotOdpowiedzialny;

    @XmlAttribute(name = "postac")
    private String postac;

    @XmlElementWrapper(name="opakowania", namespace = "http://rejestrymedyczne.csioz.gov.pl/rpl/eksport-danych-v1.0")
    @XmlElement(name="opakowanie", namespace ="http://rejestrymedyczne.csioz.gov.pl/rpl/eksport-danych-v1.0")
    public List<PackDTO> opakowania;
}
