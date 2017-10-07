package engineer.thesis.core.batch;

import engineer.thesis.core.batch.model.DrugType;
import engineer.thesis.core.model.Drug;
import engineer.thesis.core.model.dto.ExternalDrugDTO;
import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DrugProcessor implements ItemProcessor<ExternalDrugDTO, Drug> {

    private Drug emptyDrug = new Drug();

    @Setter
    private List<Drug> existingDrugs;

    @Override
    public Drug process(ExternalDrugDTO item) throws Exception {

        System.out.println("processing: " + item.getNazwaProduktu());

        Drug drug = new Drug();

        if (item.getRodzajPreparatu() == DrugType.weterynaryjny) {
            return null;
        }

        Optional<Drug> foundDrug = existingDrugs.stream().filter(d -> d.getName().equals(item.getNazwaProduktu())).findFirst();

        drug.setId(foundDrug.orElse(emptyDrug).getId());
        drug.setName(item.getNazwaProduktu());
        drug.setCommonName(item.getNazwaPowszechnieStosowana());
        drug.setDrugForm(item.getPostac());
        drug.setEntityResponsible(item.getPodmiotOdpowiedzialny());
        drug.setPermitValidity(item.getWaznoscPozwolenia());
        drug.setStrength(item.getMoc());
        drug.setRefundRate(0);
        
        return drug;
    }

}
