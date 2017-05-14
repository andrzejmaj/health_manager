package engineer.thesis.security;

import engineer.thesis.security.model.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;

@Component
public class TokenUtils {

    private static final String encodedKey = Base64.getEncoder().encodeToString("secret_super_secret".getBytes());

    private final String AUDIENCE_WEB = "web";
    private final String AUDIENCE_TABLET = "tablet";
    private final String AUDIENCE_MOBILE = "mobile";
    private final String AUDIENCE_UNKNOWN = "unknown";

    private Long expiration = 1000L;

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

    public String generateToken(UserDetails userDetails, Device device) {

        Map<String, Object> claims = new HashMap<>();


        claims.put("sub", userDetails.getUsername());
        claims.put("audience", this.generateAudience(device));
        claims.put("created", this.getCurrentDate());
        claims.put("scopes", "ROLE_PATIENT");

        return this.buildToken(claims);
    }

    public String buildToken(Map<String, Object> claims) {

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, encodedKey)
                .compact();
    }

    public String getUsername(String token) {
        try {
            final Claims claims = this.getClaims(token);
            System.out.println(claims);
            return (String) claims.get("sub");
        } catch (Exception e) {
            return null;
        }
    }

    public String getAudience(String token) {
        try {
            final Claims claims = this.getClaims(token);
            return (String) claims.get("audience");
        } catch (Exception e) {
            return null;
        }
    }

    public Date getExpirationDate(String token) {
        try {
            final Claims claims = this.getClaims(token);
            return claims.getExpiration();
        } catch (Exception e) {
            return null;
        }
    }

    public Date getCreatedDate(String token) {
        try {
            final Claims claims = this.getClaims(token);
            return (Date) claims.get("created");
        } catch (Exception e) {
            return null;
        }
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey("secret_super_secret")
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {
            return null;
        }
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

    public Boolean validateToken(String token, UserDetails userDetails) {
        SecurityUser user = (SecurityUser) userDetails;
        final String username = this.getUsername(token);
        final Date created = this.getCreatedDate(token);
        final Date expiration = this.getExpirationDate(token);
        return (username.equals(user.getUsername()) && !(this.isTokenExpired(token)));
    }
}
