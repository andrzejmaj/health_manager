package engineer.thesis.core.controller;

final class RequestMappings {

    static final class PATIENTS {
        static final String PATIENTS = "/patients";
        static final String PATIENTS_EMAIL = "/patientByMail/{email}";
        static final String PATIENTS_ID = "/patients/{id}";
        static final String EMERGENCY = "/patients/{id}/emergency";
        static final String REGISTER = "/patients/register";
        static final String PERS_DETAILS = "/patients/{id}/personalDetails";
    }

    static final class USERS {
        static final String LOGIN = "/users/login";
        static final String REGISTER = "/users/register";
        static final String RESET_PASSWORD = "/users/password/reset";
        static final String UPDATE_PASSWORD_WITH_TOKEN = "/users/password_token";
        static final String UPDATE_PASSWORD = "/users/password";
        static final String UPDATE_EMAIL = "/users/{id}/email";
        static final String REGISTER_ON_BEHALF = "/users/registerOnBehalf";
        static final String ACTIVATE = "/users/{id}/activate";
    }

    static final class ACCOUNTS {
        static final String MY_PERS_DETAILLS = "/accounts/personaldetails";
        static final String ACCOUNTS_PICTURE = "/accounts/{id}/picture";
    }

    static final class FORMS {
        static final String FORMS = "/forms";
        static final String FORM = "/forms/{id}";
        static final String FORMS_OWNER_ID = "/forms/owner/{id}";
        static final String FORMS_NAME = "/forms/name/{name}";
        static final String FORM_DEFAULTS = "/forms/{id}/defaults";
        static final String FORMS_DEFAULTS_ID = "/forms/defaults/{id}";
    }

    static final class HISTORY {
        static final String PATIENT_HISTORY = "/patients/{patientId}/history";
        static final String PATIENT_HISTORY_ID = "/patients/{patientId}/history/{id}";
    }

    static final class CURRENT_CONDITION {
        static final String CURRENT_CONDITION = "patients/{patientId}/currentCondition";
        static final String CURRENT_CONDITION_ID = "patients/{patientId}/currentCondition/{id}";

    }

    static final class CHECKUP {
        static final String PATIENT_CHECKUPS = "/patients/{patientId}/checkups";
        static final String CHECKUP_ID = "/checkups/{id}";
        static final String PATIENT_CHECKUPS_BY_NAME = "/patients/{patientId}/checkups?name=";
    }

    static final class MEDICAL {
        static final String PATIENT_MEDICAL = "/patients/{patientId}/medicalInformation";
    }

    static final class DRUGS {
        static final String DRUGS = "/drugs";
        static final String DRUGS_ID = "/drugs/{id}";
    }

    static final class PRESCRIPTIONS {
        static final String PRESCRIPTIONS = "/prescriptions";
        static final String PRESCRIPTIONS_ID = "/prescriptions/{id}";
    }
}

