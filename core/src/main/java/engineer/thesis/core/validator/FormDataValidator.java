package engineer.thesis.core.validator;

import engineer.thesis.core.model.entity.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Component
public class FormDataValidator {

    private String errorMessage;

    public Boolean isDataValid(List<MedicalCheckupValue> dataList, Form form) {
        return dataList.stream().noneMatch(entry -> isEntryValid(entry.getValue(), getFormField(entry.getFormFieldId(), form)).equals(false));
    }

    public Boolean isDataValidDefault(List<FormFieldDefaultValue> dataList, Form form) {
        return dataList.stream().noneMatch(entry -> isEntryValid(entry.getValue(), getFormField(entry.getName(), form)).equals(false));
    }

    private Boolean isEntryValid(String value, Optional<FormField> formField) {

        Boolean isValid = true;

        if (!formField.isPresent()) {
            setErrorMessage("Field definition for value: " + value + " does not exist");
            return false;
        }

        FormField field = formField.get();

        if (Boolean.TRUE.equals(field.getIsRequired()) && value.isEmpty()) {
            setErrorMessage("Field " + field.getName() + " is required");
            return false;
        }

        List<FormAvailableValue> fieldAvailableValues = field.getOptions();

        if (fieldAvailableValues != null && !fieldAvailableValues.isEmpty()) {
            boolean invalid = fieldAvailableValues.stream().noneMatch(formAvailableValue -> formAvailableValue.getValue().equals(value));
            if (invalid) {
                setErrorMessage(value + " is not applicable for field " + field.getName());
            }
            return !invalid;
        }

        switch (field.getType()) {
            case NUMERIC:
                isValid = isNumericFieldValid(value, field);
                break;
            case DATE:
                isValid = isDateFieldValid(value);
                break;
            case INPUT:
                isValid = isTextFieldValid(value, field);
                break;
            case CHECKBOX:
                isValid = isCheckboxValid(value, field);
                break;
            case EMAIL:
                isValid = isEmailValid(value);
                break;
            case SELECT:
                isValid = isSelectFieldValid(value, field);
                break;
            case TEXTAREA:
                isValid = isTextareaValid(value, field);
                break;
        }


        if (!isValid) {
            setErrorMessage("Field " + field.getName() + " of type " + field.getType() + " with value " + value + " is not valid");
        }

        return isValid;
    }

    private Optional<FormField> getFormField(Long fieldId, Form form) {
        return form.getFormFields().stream().filter(field -> Objects.equals(field.getId(), fieldId)).findFirst();
    }

    private Optional<FormField> getFormField(String name, Form form) {
        return form.getFormFields().stream().filter(field -> Objects.equals(field.getName(), name)).findFirst();
    }

    private Boolean isCheckboxValid(String value, FormField field) {
        return "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value);
    }

    private Boolean isTextFieldValid(String value, FormField field) {
        return true;
    }

    private Boolean isNumericFieldValid(String value, FormField field) {
        return isNumeric(value);
    }

    private Boolean isSelectFieldValid(String value, FormField field) {
        return field.getOptions().stream().anyMatch(f -> f.getValue().equals(value));
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

    private Boolean isTextareaValid(String value, FormField field) {
        return true;
    }
}
