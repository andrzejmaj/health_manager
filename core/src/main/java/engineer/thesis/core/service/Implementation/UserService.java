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
import engineer.thesis.core.model.dto.RegisterRequestDTO;
import engineer.thesis.core.service.Interface.IUserService;
import engineer.thesis.core.utils.CustomObjectMapper;
import engineer.thesis.core.utils.MailService;
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

    @Autowired
    private MailService mailService;

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    // TODO: 11/10/2017
    // Ask PRZEMEK about doctor save

    @Override
    public void registerUserByRole(RegisterRequestDTO request, UserRole userRole, Boolean isOnBehalf) throws AlreadyExistsException {

        if (isOnBehalf) {
            request.setPassword(generatePasswordForUser());
        }

        User user = registerNewUser(request, userRole, !isOnBehalf);

        Account account = accountService.createAccount(objectMapper.convert(request.getPersonalDetails(), PersonalDetails.class), user);

        switch (userRole) {
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

        if (!user.getIsActive()) {
            mailService.send(mailService.constuctUserCreationEmail(user.getEmail(), request.getPassword()));
        }
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

    private User registerNewUser(RegisterRequestDTO registerRequest, UserRole role, boolean isActive) throws AlreadyExistsException {

        if (userExists(registerRequest.getEmail())) {
            throw new AlreadyExistsException("User with such mail already exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(role);
        user.setIsActive(isActive);

        return userRepository.save(user);
    }

    private boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
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

    private String generatePasswordForUser() {
        return UUID.randomUUID().toString();
    }

}