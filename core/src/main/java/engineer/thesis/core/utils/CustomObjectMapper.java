package engineer.thesis.core.utils;


import engineer.thesis.core.model.dto.*;
import engineer.thesis.core.model.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

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
        modelMapper.addMappings(new MedicalCheckupToResponseMedicalCheckupDTO());
        modelMapper.addMappings(new FormFieldDTOToModel());
        modelMapper.addMappings(new FormFieldModelToDTO());
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

    private class MedicalCheckupToResponseMedicalCheckupDTO extends PropertyMap<MedicalCheckup, ResponseMedicalCheckupDTO> {
        @Override
        protected void configure() {
            map().setMedicalCheckupValues(null);
        }
    }

    private class FormFieldModelToDTO extends PropertyMap<FormField, FormFieldDTO> {
        @Override
        protected void configure() {
            map().setOptions(source.getOptions().stream().map(FormAvailableValue::getValue).collect(Collectors.toList()));
        }
    }

    private class FormFieldDTOToModel extends PropertyMap<FormFieldDTO, FormField> {
        @Override
        protected void configure() {
            map().setOptions(source.getOptions().stream().map(op -> new FormAvailableValue(null, map(), op)).collect(Collectors.toList()));
        }
    }
}
