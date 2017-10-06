package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.TokenExpiredException;
import engineer.thesis.core.model.*;
import engineer.thesis.core.model.dto.ResetPasswordDTO;
import engineer.thesis.core.model.dto.UserDTO;
import engineer.thesis.core.repository.DoctorRepository;
import engineer.thesis.core.repository.PasswordResetTokenRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.repository.UserRepository;
import engineer.thesis.core.security.model.PasswordResetToken;
import engineer.thesis.core.security.model.RegisterOnBehalfRequest;
import engineer.thesis.core.security.model.RegisterRequest;
import engineer.thesis.core.service.Interface.IUserService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.*;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomObjectMapper objectMapper;

    //@Value("${files.rootDirectory}")
    private String rootDirectory = "/Users/andrzejmaj123/Documents/health_manager/core/src/main/resources/images/";

    private Path path;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AccountService accountService;

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public String registerNewUserOnBehalf(RegisterOnBehalfRequest request) throws AlreadyExistsException {
        User user = this.registerNewUser(request, request.getRole());

        Account account = accountService.newAccount(
                request.getPersonalDetails() != null ?
                        objectMapper.convert(request.getPersonalDetails(), PersonalDetails.class)
                        : null,
                user);

        switch (request.getRole()) {
            case ROLE_PATIENT: {
                Patient patient = new Patient();
                patient.setInsuranceNumber(request.getInsuranceNumber());
                patient.setAccount(account);
                patientRepository.save(patient);
                break;
            }
            case ROLE_DOCTOR: {
                Doctor doctor = new Doctor();
                doctor.setAccount(account);

                doctorRepository.save(doctor);
                break;
            }
            case ROLE_ADMIN: {
                break;
            }
        }

        return "OK";
    }

    @Override
    public UserDTO register(RegisterRequest registerRequest, UserRole role) throws AlreadyExistsException {
        return objectMapper.convert(registerNewUser(registerRequest, role), UserDTO.class);
    }

    private User registerNewUser(RegisterRequest registerRequest, UserRole role) throws AlreadyExistsException {
        if (userExists(registerRequest.getEmail())) {
            throw new AlreadyExistsException("User with such mail already exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {

        if (!userExists(userDTO.getEmail())) {
            throw new NoSuchElementException("User not found");
        }

        User user = userRepository.save(objectMapper.convert(userDTO, User.class));

        return objectMapper.convert(user, UserDTO.class);

    }

    @Override
    public String changeUserPassword(String email, String password) {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        if (!user.isPresent()) {
            throw new NoSuchElementException("User not found");
        }

        user.get().setPassword(passwordEncoder.encode(password));
        userRepository.save(user.get());

        return "Password has been changed successfully";
    }

    @Override
    public String changeUserPasswordWithToken(String email, String token, String password) throws TokenExpiredException, NoSuchElementException {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        if (!user.isPresent()) {
            throw new NoSuchElementException("User not found");
        }

        if (isResetPasswordTokenValid(email, token)) {
            throw new TokenExpiredException("Token is not valid");
        }

        user.get().setPassword(passwordEncoder.encode(password));
        userRepository.save(user.get());

        return "Password has been changed successfully";
    }

    @Override
    public String updateUserEmail(Long id, String newEmail) {
        Optional<User> user = Optional.ofNullable(userRepository.findOne(id));

        if (!user.isPresent()) {
            throw new NoSuchElementException("User not found");
        }

        user.get().setEmail(newEmail);
        userRepository.save(user.get());

        return "Email updated successfully";
    }

    public ResetPasswordDTO resetUserPassword(String email) throws NoSuchElementException {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (!user.isPresent()) {
            throw new NoSuchElementException("User not found");
        }

        String token = UUID.randomUUID().toString();
        //TODO:
        // add to reset password token table isActive
        // property and disable it after used
        createPasswordResetTokenForUser(user.get(), token);
        ResetPasswordDTO resetPassword = new ResetPasswordDTO();
        resetPassword.setEmail(user.get().getEmail());
        resetPassword.setUserId(user.get().getId());
        resetPassword.setToken(token);

        return resetPassword;
    }

    private boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean userExists(Long id) {
        return userRepository.findOne(id) != null;
    }

    private Boolean isResetPasswordTokenValid(String email, String token) {
        System.out.println(email);
        PasswordResetToken passwordToken =
                passwordTokenRepository.findByToken(token);
        System.out.println(passwordToken.getUser().getEmail());
        if (!Objects.equals(passwordToken.getUser().getEmail(), email)) {
            System.out.println("Email does not match");
            return false;
        }

        Calendar cal = Calendar.getInstance();
        return (passwordToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) > 0;
    }

    private void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(resetToken);
    }

}