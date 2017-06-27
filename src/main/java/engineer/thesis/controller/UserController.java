package engineer.thesis.controller;

import engineer.thesis.model.User;
import engineer.thesis.security.model.SecurityUser;
import engineer.thesis.security.model.UpdatePasswordRequest;
import engineer.thesis.service.UserService;
import engineer.thesis.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;


    @RequestMapping(path = "/users/resetPassword", method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(HttpServletRequest request,
                                           @RequestBody String email) {

        System.out.println("Inside reset password controller");
        Optional<User> user = userService.findByEmail(email);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("User not found");
        }
        System.out.println("Reset password User found: " + user.get());
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user.get(), token);
        mailService.send(mailService.constructResetTokenEmail(request.getPathInfo(),
                request.getLocale(), token, user.get()));

        return ResponseEntity.ok("Operation successful");
    }

    @RequestMapping(path = "/users/updatePasswordWithToken", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody String newPassword,
                                            @RequestParam("email") String email,
                                            @RequestParam("token") String token) {
        if (!userService.isResetPasswordTokenValid(email, token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token is not valid");
        }

        userService.changeUserPassword(email, newPassword);
        return ResponseEntity.ok("Operation successful");
    }

    @RequestMapping(path = "/users/updatePassword", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody UpdatePasswordRequest updatePasswordData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("IS AUTHENTICATED: " + authentication.isAuthenticated());
        System.out.println("GET PRINCIPAL: " + authentication.getPrincipal());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        System.out.println(securityUser.getUsername());
        System.out.println(securityUser.getPassword());
        userService.changeUserPassword(securityUser.getUsername(), updatePasswordData.getPassword());
        return ResponseEntity.ok("Operation successful");
    }

    @RequestMapping(path = "/users/{id}/updateEmail", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long id, @RequestBody String email) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        if (!userService.canPerformUserAction(id, securityUser)) {
            ResponseEntity.status(HttpStatus.FORBIDDEN);
        }

        userService.updateUserEmail(id, email);

        return ResponseEntity.ok("Operation successful");
    }
}
