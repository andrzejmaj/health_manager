package engineer.thesis.core.utils;


import engineer.thesis.core.model.FormFieldDefaultValue;
import engineer.thesis.core.model.FormFieldType;
import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.Prescription;
import engineer.thesis.core.model.dto.FormFieldDefaultValueDTO;
import engineer.thesis.core.model.dto.PrescriptionDTO;
import engineer.thesis.core.model.dto.ShortPatientDTO;
import engineer.thesis.core.model.enums.FieldType;
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

    public <OriginType, ReturnedType> ReturnedType convert(OriginType object, Class<ReturnedType> returnedClass) {
        return modelMapper.map(object, returnedClass);
    }

    @PostConstruct
    public void setupMappings() {
        modelMapper.addMappings(new PatientShortPatientMap());
        modelMapper.addMappings(new FormFieldDefaultValueDTOToModel());
        modelMapper.addMappings(new PrescriptionDTOToModel());
        modelMapper.createTypeMap(String.class, FormFieldType.class).setConverter(context -> context.getSource() == null ? null : new FormFieldType(null, FieldType.valueOf(context.getSource())));
    }

    private class PatientShortPatientMap extends PropertyMap<Patient, ShortPatientDTO> {
        @Override
        protected void configure() {
            map().setFirstName(source.getAccount().getPersonalDetails().getFirstName());
            map().setLastName(source.getAccount().getPersonalDetails().getLastName());
            map().setBirthdate(source.getAccount().getPersonalDetails().getBirthDate());
        }
    }

    private class FormFieldDefaultValueDTOToModel extends PropertyMap<FormFieldDefaultValueDTO, FormFieldDefaultValue> {
        @Override
        protected void configure() {
            map().setId(null);
        }
    }

    private class PrescriptionDTOToModel extends PropertyMap<PrescriptionDTO, Prescription> {
        @Override
        protected void configure() {
            map().setDrugs(null);
        }
    }

}
