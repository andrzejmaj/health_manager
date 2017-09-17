package engineer.thesis.core.utils;


import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.dto.ShortPatientDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CustomObjectMapper {

    private ModelMapper modelMapper;

    public CustomObjectMapper() {
        modelMapper = new ModelMapper();
    }

    public <OriginType, ReturnedType> ReturnedType convert(OriginType object, Class<ReturnedType> contentClass) {
        return modelMapper.map(object, contentClass);
    }

    @PostConstruct
    public void setupMappings() {
        modelMapper.addMappings(new PatientShortPatientMap());

    }

    private class PatientShortPatientMap extends PropertyMap<Patient, ShortPatientDTO> {
        @Override
        protected void configure() {
            map().setFirstName(source.getAccount().getPersonalDetails().getFirstName());
            map().setLastName(source.getAccount().getPersonalDetails().getLastName());
            map().setBirthdate(source.getAccount().getPersonalDetails().getBirthDate());
        }
    }
}
