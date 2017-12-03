package engineer.thesis.core.controller;

public final class RequestMappings {

    public static final class PATIENTS {
        public static final String PATIENTS = "/patients";
        public static final String PATIENTS_EMAIL = "/patientByMail/{email}";
        public static final String PATIENTS_ID = "/patients/{id}";
        public static final String EMERGENCY = "/patients/{id}/emergency";
        public static final String REGISTER = "/patients/register";
        public static final String PERS_DETAILS = "/patients/{id}/personalDetails";
    }

    public static final class USERS {
        public static final String LOGIN = "/users/login";
        public static final String REGISTER = "/users/register";
        public static final String RESET_PASSWORD = "/users/password/reset";
        public static final String UPDATE_PASSWORD_WITH_TOKEN = "/users/password_token";
        public static final String UPDATE_PASSWORD = "/users/password";
        public static final String UPDATE_EMAIL = "/users/{id}/email";
        public static final String REGISTER_ON_BEHALF = "/users/registerOnBehalf";
        public static final String ACTIVATE = "/users/{id}/activate";
    }

    public static final class ACCOUNTS {
        public static final String MY_PERS_DETAILS = "/accounts/personaldetails";
        public static final String MY_EMERGENCY_CONTACT = "/accounts/emergency";
        public static final String MY_MEDICAL = "/accounts/medical";
        public static final String ACCOUNTS_PICTURE = "/accounts/{id}/picture";
    }

    public static final class FORMS {
        public static final String FORMS = "/forms";
        public static final String FORM = "/forms/{id}";
        public static final String FORM_DEFAULTS = "/forms/{id}/defaults";
        public static final String FORMS_DEFAULTS_ID = "/forms/defaults/{id}";
    }

    public static final class HISTORY {
        public static final String PATIENT_HISTORY = "/patients/{patientId}/history";
        public static final String PATIENT_HISTORY_ID = "/patients/history/{id}";
    }

    public static final class CURRENT_CONDITION {
        public static final String CURRENT_CONDITION = "patients/{patientId}/currentCondition";
        public static final String CURRENT_CONDITION_ID = "patients/{patientId}/currentCondition/{id}";

    }

    public static final class CHECKUP {
        public static final String PATIENT_CHECKUPS = "/checkups";
        public static final String CHECKUP_ID = "/checkups/{id}";
    }

    public static final class MEDICAL {
        public static final String PATIENT_MEDICAL = "/patients/{patientId}/medicalInformation";
    }

    public static final class DRUGS {
        public static final String DRUGS = "/drugs";
        public static final String DRUGS_ID = "/drugs/{id}";
    }

    public static final class PRESCRIPTIONS {
        public static final String PRESCRIPTIONS = "/prescriptions";
        public static final String PRESCRIPTIONS_ID = "/prescriptions/{id}";
    }
}

