package engineer.thesis.core.model.dto;

import engineer.thesis.core.batch.model.DrugType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "produktLeczniczy", namespace = "http://rejestrymedyczne.csioz.gov.pl/rpl/eksport-danych-v1.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExternalDrugDTO {

    @XmlAttribute(name = "nazwaProduktu")
    private String nazwaProduktu;

    @XmlAttribute(name = "rodzajPreparatu")
    private DrugType rodzajPreparatu;

}
