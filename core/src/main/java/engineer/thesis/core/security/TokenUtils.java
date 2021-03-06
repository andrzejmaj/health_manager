package engineer.thesis.core.security;

import engineer.thesis.core.security.model.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

    private final String AUDIENCE_WEB = "web";
    private final String AUDIENCE_TABLET = "tablet";
    private final String AUDIENCE_MOBILE = "mobile";
    private final String AUDIENCE_UNKNOWN = "unknown";

    private Long expiration = 65000L;

    private Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDate(token);
        return expiration.before(this.getCurrentDate());
    }

    public String generateToken(SecurityUser securityUser, Device device) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("sub", securityUser.getUsername());
        claims.put("passwordChangeAdvised", !securityUser.getIsActive());
        claims.put("created", this.getCurrentDate());
        claims.put("scopes", securityUser.getRole());

        return this.buildToken(claims);
    }

    private String buildToken(Map<String, Object> claims) {

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, "secret_super_secret")
                .compact();
    }

    String getUsername(String token) {
        final Claims claims = this.getClaims(token);
        System.out.println(claims);
        return (String) claims.get("sub");
    }

    private String getAudience(String token) {
        try {
            final Claims claims = this.getClaims(token);
            return (String) claims.get("audience");
        } catch (Exception e) {
            return null;
        }
    }

    private Date getExpirationDate(String token) {
        try {
            final Claims claims = this.getClaims(token);
            return claims.getExpiration();
        } catch (Exception e) {
            return null;
        }
    }

    private Date getCreatedDate(String token) {
        try {
            final Claims claims = this.getClaims(token);
            return (Date) claims.get("created");
        } catch (Exception e) {
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey("secret_super_secret")
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateAudience(Device device) {
        String audience = this.AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = this.AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = this.AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = this.AUDIENCE_MOBILE;
        }
        return audience;
    }

    Boolean validateToken(String token, UserDetails userDetails) {
        SecurityUser user = (SecurityUser) userDetails;
        final String username = this.getUsername(token);
        return (username.equals(user.getUsername()) && !(this.isTokenExpired(token)));
    }
}
