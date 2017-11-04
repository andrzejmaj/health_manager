package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.DrugDTO;
import engineer.thesis.core.model.dto.ExtendedDrugDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDrugService {

    List<DrugDTO> findAll();

    List<DrugDTO> findAllByName(String name);

    ExtendedDrugDTO findById(Long id) throws NoSuchElementExistsException;

    Page<DrugDTO> findAllPageable(String name, Pageable pageable);

}
