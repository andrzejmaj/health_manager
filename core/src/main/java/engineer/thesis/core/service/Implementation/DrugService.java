package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.Drug;
import engineer.thesis.core.model.dto.DrugDTO;
import engineer.thesis.core.repository.DrugRepository;
import engineer.thesis.core.service.Interface.IDrugService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrugService implements IDrugService {

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public List<DrugDTO> findAll() {
        return drugRepository.findAll().stream().map(d -> objectMapper.convert(d, DrugDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<DrugDTO> findAllByName(String name) {
        return drugRepository.findByNameContainingIgnoreCase(name).stream().map(d -> objectMapper.convert(d, DrugDTO.class)).collect(Collectors.toList());
    }

    @Override
    public DrugDTO findById(Long id) throws NoSuchElementExistsException {
        Drug drug = drugRepository.findOne(id);
        if (drug == null) {
            throw new NoSuchElementExistsException("Drug doesn't exist");
        }
        return objectMapper.convert(drug, DrugDTO.class);
    }

    @Override
    public Page<DrugDTO> findAllPageable(String name, Pageable pageable) {
        return drugRepository.findAllByNameContainingIgnoreCase(name, pageable).map(drug -> objectMapper.convert(drug, DrugDTO.class));
    }

}
