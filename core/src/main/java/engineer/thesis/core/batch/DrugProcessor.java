package engineer.thesis.core.batch;

import engineer.thesis.core.batch.model.DrugType;
import engineer.thesis.core.model.Drug;
import engineer.thesis.core.model.dto.ExternalDrugDTO;
import engineer.thesis.core.repository.DrugRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class DrugProcessor implements ItemProcessor<ExternalDrugDTO, Drug> {

    @Autowired
    private DrugRepository drugRepository;

    private Drug emptyDrug = new Drug();

    private List<Drug> existingDrugs;

    @PostConstruct
    public void getDrugList() {
        existingDrugs = drugRepository.findAll();
    }

    @Override
    public Drug process(ExternalDrugDTO item) throws Exception {
        System.out.println("processing: " + item.getRodzajPreparatu());
        Drug drug = new Drug();
        if (item.getRodzajPreparatu() == DrugType.weterynaryjny ) {
            return null;
        }
        Optional<Drug> foundDrug = existingDrugs.stream().filter(d -> d.getName().equals(item.getNazwaProduktu())).findFirst();
        drug.setId(foundDrug.orElse(emptyDrug).getId());

        drug.setName(item.getNazwaProduktu());
        drug.setRefundRate(0);
        return drug;
    }

}
