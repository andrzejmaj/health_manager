package engineer.thesis.controller;

final class RequestMappings {

    static final class USERS {
        static final String LOGIN = "/users/login";
        static final String REGISTER = "/users/register";
        static final String RESET_PASSWORD = "/users/password/reset";
        static final String UPDATE_PASSWORD_WITH_TOKEN = "/users/password_token";
        static final String UPDATE_PASSWORD = "/users/password";
        static final String UPDATE_EMAIL = "/users/{id}/email";
        static final String USR_DET = "/users/personaldetails/{id}";
        static final String USR_OWN_DET = "/users/personaldetails";
    }

    static final class ACCOUNTS {
        static final String ACCOUNTS = "/accounts";
        static final String ACCOUNT = "/accounts/{id}";
        static final String PERS_DETAILS = "/accounts/{id}/personaldetails";
        static final String MY_PERS_DETAILLS = "/accounts/personaldetails";
    }
}

