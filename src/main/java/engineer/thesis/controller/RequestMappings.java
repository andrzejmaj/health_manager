package engineer.thesis.controller;

final class RequestMappings {

    static final class USERS {
        static final String LOGIN = "/users/login";
        static final String REGISTER = "/users/register";
        static final String RESET_PASSWORD = "/users/password/reset";
        static final String UPDATE_PASSWORD_WITH_TOKEN = "/users/password_token/";
        static final String UPDATE_PASSWORD = "/users/password";
        static final String UPDATE_EMAIL = "/users/{id}/email";
        static final String USER_OWN_PERSONAL_DETAILS = "/users/personaldetails";
    }
}

