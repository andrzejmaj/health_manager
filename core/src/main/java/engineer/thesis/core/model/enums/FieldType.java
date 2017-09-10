package engineer.thesis.core.model.enums;


public enum  FieldType {
    CHECKBOX("CHECKBOX"),
    DATE_FIELD("DATE_FIELD"),
    EMAIL_FIELD("EMAIL_FIELD"),
    NUMERIC("NUMERIC"),
    TEXT_FIELD("TEXT_FIELD"),
    SELECT("SELECT");

    private String value;

    FieldType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
