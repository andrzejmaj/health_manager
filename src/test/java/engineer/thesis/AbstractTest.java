package engineer.thesis;


import engineer.thesis.core.model.User;
import engineer.thesis.core.model.UserRole;
import engineer.thesis.core.repository.UserRepository;
import engineer.thesis.core.security.AnonAuthentication;
import engineer.thesis.core.security.model.SecurityUser;
import engineer.thesis.core.security.model.SecurityUserFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@SpringBootTest(classes = { HealthManagerApplication.class })
public abstract class AbstractTest {

    @Autowired
    protected UserRepository userRepository;

    @Before
    public final void beforeAbstractTest() {
        securityContext = Mockito.mock( SecurityContext.class );
        SecurityContextHolder.setContext( securityContext );
        Mockito.when( securityContext.getAuthentication() ).thenReturn( new AnonAuthentication() );
    }

    @After
    public final void afterAbstractTest() {
        SecurityContextHolder.clearContext();
    }

    protected SecurityContext securityContext;

    protected void mockAuthenticatedUser( SecurityUser user ) {
        mockAuthentication( new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    }

    private void mockAuthentication( UsernamePasswordAuthenticationToken auth ) {
        //auth.setAuthenticated( true );
        Mockito.when( securityContext.getAuthentication() ).thenReturn( auth );
    }


    protected SecurityUser buildTestUserWithRole(UserRole role) {
        User user = new User();
        user.setEmail("user@email.com");
        user.setPassword("password");
        user.setRole(role);

        return SecurityUserFactory.create(user);
    }
}