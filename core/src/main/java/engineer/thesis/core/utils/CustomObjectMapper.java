package engineer.thesis.core.utils;


import engineer.thesis.core.model.dto.*;
import engineer.thesis.core.model.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

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
        modelMapper.addMappings(new MedicalHistoryDTOToModel());

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

    private class MedicalHistoryDTOToModel extends PropertyMap<RequestMedicalHistoryDTO, MedicalHistory> {
        @Override
        protected void configure() {
            map().setDetectionDate(source.getDetectionDate() == null ? new Date() : source.getDetectionDate());
        }
    }
}
