package engineer.thesis.model.dto;

import engineer.thesis.model.Drug;
import lombok.Value;

@Value
public class DrugDTO {

    private Long id;
    private String name;
    private Integer refundRate;

    public DrugDTO(Drug drug) {
        this.id = drug.getId();
        this.name = drug.getName();
        this.refundRate = drug.getRefundRate();
    }
}
