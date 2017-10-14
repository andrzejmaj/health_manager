package engineer.thesis.core.validator;

import engineer.thesis.core.model.Form;
import engineer.thesis.core.model.FormField;
import engineer.thesis.core.model.MedicalCheckup;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Optional;

@Component
public class MedicalCheckupValidator {

    private String errorMessage;

    public Boolean isMedicalCheckupValid(MedicalCheckup medicalCheckup, Form form) {
        return !medicalCheckup.getMedicalCheckupValues().stream().anyMatch(entry -> isEntryValid(entry.getValue(), getFormField(entry.getFieldId(), form)).equals(false));
    }

    private Boolean isEntryValid(String value, Optional<FormField> formField) {

        Boolean isValid = true;

        if (!formField.isPresent()) {
            setErrorMessage("Field definition for value: " + value + " does not exist");
            return false;
        }

        FormField field = formField.get();

        if (field.getIsRequired() && value.isEmpty()) {
            setErrorMessage("Field " + field.getName() + " is required");
            return false;
        }

        switch (field.getType().getType()) {
            case NUMERIC:
                isValid = isNumericFieldValid(value, field);
                break;
            case DATE:
                isValid = isDateFieldValid(value);
                break;
            case TEXTFIELD:
                isValid = isTextFieldValid(value, field);
                break;
            case CHECKBOX:
                break;
            case EMAIL:
                isValid = isEmailValid(value);
                break;
            case SELECT:
                isSelectFieldValid(value, field);
                break;
        }

        if (!isValid) {
            setErrorMessage("Field " + field.getName() + " of type " + field.getType() + " with value " + value + " is not valid");
        }

        return true;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private Optional<FormField> getFormField(Long fieldId, Form form) {
        return form.getFormFields().stream().findFirst().filter(field -> Objects.equals(field.getId(), fieldId));
    }

    private Boolean isTextFieldValid(String value, FormField field) {
        return true;
    }

    private Boolean isNumericFieldValid(String value, FormField field) {
        return isNumeric(value);
    }

    private Boolean isSelectFieldValid(String value, FormField field) {
        return field.getFieldAvailableValues().stream().anyMatch(f -> f.getValue().equals(value));
    }

    private Boolean isEmailValid(String value) {
        return true;
    }

    private Boolean isDateFieldValid(String value) {
        return isValidDate(value);
    }

    private static boolean isNumeric(String value) {
        return value.matches("^(?:(?:\\-{1})?\\d+(?:\\.{1}\\d+)?)$");
    }

    private static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

}
