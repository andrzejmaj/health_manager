package engineer.thesis.core.service.Interface;

import engineer.thesis.core.model.dto.DrugDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDrugService {

    List<DrugDTO> findAll();

    List<DrugDTO> findAllByName(String name);
}
