package engineer.thesis.core.model.dto;

import engineer.thesis.core.batch.model.DrugType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalDrugDTO {

    private String nazwaProduktu;
    private DrugType rodzajPreparatu;

}
