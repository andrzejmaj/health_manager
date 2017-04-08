package engineer.thesis.service;

import engineer.thesis.model.User;

public interface IUserService {

    User findByEmail(String email);

}
