package engineer.thesis.core.controller;

final class RequestMappings {

    static final class PATIENTS {
        static final String PATIENTS = "/patients";
        static final String PATIENTS_ID = "/patients/{id}";
        static final String EMERGENCY = "/patients/{id}/emergency";
    }

    static final class USERS {
        static final String LOGIN = "/users/login";
        static final String REGISTER = "/users/register";
        static final String RESET_PASSWORD = "/users/password/reset";
        static final String UPDATE_PASSWORD_WITH_TOKEN = "/users/password_token";
        static final String UPDATE_PASSWORD = "/users/password";
        static final String UPDATE_EMAIL = "/users/{id}/email";
    }

    static final class ACCOUNTS {
        static final String ACCOUNTS = "/accounts";
        static final String ACCOUNTS_ID = "/accounts/{id}";
        static final String PERS_DETAILS = "/accounts/{id}/personaldetails";
        static final String MY_PERS_DETAILLS = "/accounts/personaldetails";
        static final String ACCOUNTS_PICTURE = "/accounts/{id}/picture";
        static final String ACCOUNTS_ROLE = "/accounts/role";
    }

    static final class FORMS {
        static final String FORMS = "/forms";
        static final String FORM = "/forms/{id}";
        static final String FORMS_OWNER_ID = "/forms/owner/{id}";
        static final String FORMS_NAME = "/forms/name/{name}";
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
        static final String PATIENT_CHECKUP = "/patients/{patientId}/checkups";
        static final String PATIENT_CHECKUP_ID = "/patients/{patientId}/checkups/{id}";
    }

    static final class MEDICAL_INFO {
        static final String PATIENT_MEDICAL = "/patients/{patientId}/medicalInformation";
        static final String PATIENT_MEDICAL_ID = "/patients/{patientId}/medicalInformation/{id}";
    }

    static final class PERS_DETAILS {
        static final String MY_PERS_DETAILS = "/accounts/personalDetails";
        static final String PATIENT_PERS_DETAILS = "patients/{patientId}/personalDetails";
        static final String DOCTOR_PERS_DETAILS = "doctors/{doctorId}/personalDetails";
    }

    static final class DOCTORS {
        static final String DOCTORS = "/doctors";
        static final String DOCTORS_ID = "/doctors/{doctorId}";
    }

    static final class DRUGS {
        static final String DRUGS = "/drugs";
        static final String DRUGS_ID = "/drugs/{id}";
    }
}

