package engineer.thesis.core.batch;

import engineer.thesis.core.model.Drug;
import engineer.thesis.core.repository.DrugRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DrugWriter implements ItemWriter<Drug> {

    @Autowired
    private DrugRepository drugRepository;

    @Override
    public void write(List<? extends Drug> items) throws Exception {
        drugRepository.save(items);
    }
}
