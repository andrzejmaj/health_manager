package engineer.thesis.core.model.enums;


public enum FieldType {
    CHECKBOX("CHECKBOX"),
    DATE("DATE"),
    EMAIL("EMAIL"),
    NUMERIC("NUMERIC"),
    INPUT("INPUT"),
    SELECT("SELECT");

    private String value;

    FieldType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
