package mo.gov.dsscu.dss3.utils;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtils {

  @Value("${jwt.secret}")
  private String secret;

  public String getSecret() {
    return secret;
  }

  public String createJwt(String subject, int calendarUnit, int amount) {
    return createJwt(subject, calendarUnit, amount, new HashMap<String, Object>());
  }

  public String createJwt(String subject, int calendarUnit, int amount, Map<String, Object> cliams) {
    Date now = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(now);
    calendar.add(calendarUnit, amount);
    Date newDate = calendar.getTime();
    return createJwt(subject, newDate, cliams);
  }

  public String createJwt(String subject, Date expirationDate) {
    return createJwt(subject, expirationDate, new HashMap<String, Object>());
  }

  public String createJwt(String subject, Date expirationDate, Map<String, Object> cliams) {
    JwtBuilder builder = Jwts.builder()
        .signWith(getSigningKey())
        .setSubject(subject)
        .setIssuedAt(new Date())
        .setExpiration(expirationDate);
    for (Map.Entry<String, Object> entry : cliams.entrySet()) {
      builder.claim(entry.getKey(), entry.getValue());
    }
    return builder.compact();
  }

  public boolean validateJwt(String jwt) {
    try {
      parserBuilder()
          .build()
          .parseClaimsJws(jwt);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String extractSubject(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public Claims extractClaims(String token) {
    return extractAllClaims(token);
  }

  private Claims extractAllClaims(String token) {
    return parserBuilder()
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private JwtParserBuilder parserBuilder() {
    return Jwts.parserBuilder().setSigningKey(getSigningKey());
  }

  private Key getSigningKey() {
    return io.jsonwebtoken.security.Keys.hmacShaKeyFor(secret.getBytes());
  }
}
