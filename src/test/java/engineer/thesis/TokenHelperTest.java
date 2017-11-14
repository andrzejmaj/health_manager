package engineer.thesis;

import engineer.thesis.core.security.TokenUtils;
import org.junit.Ignore;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;

@Ignore
public class TokenHelperTest {

    private TokenUtils tokenHelper;
    private Device device;
    private UserDetails userDetails;

    private final String USER_EMAIL = "sample@mail.com";
/*
    @Before
    public void init() {
        tokenHelper = new TokenUtils();
        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 1);
        ReflectionTestUtils.setField(tokenHelper, "SECRET", "mySecret");

        User user = new User();
        user.setEmail(USER_EMAIL);

        UserDetails userDetails = SecurityUserFactory.create(user);

        Device device = new Device() {
            @Override
            public boolean isNormal() {
                return false;
            }

            @Override
            public boolean isMobile() {
                return false;
            }

            @Override
            public boolean isTablet() {
                return false;
            }
        };
    }

    @Test(expected=ExpiredJwtException.class)
    public void testGenerateTokenExpired() {
        String token = tokenHelper.generateToken(userDetails, device);
        Jwts.parser()
                .setSigningKey("mySecret")
                .parseClaimsJws(token)
                .getBody();
    }
*/
}