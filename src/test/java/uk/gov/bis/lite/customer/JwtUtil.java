package uk.gov.bis.lite.customer;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwx.HeaderParameterNames;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

public class JwtUtil {
  public static String generateToken(String jwtSharedSecret, String subject) {
    JwtClaims claims = new JwtClaims();
    claims.setIssuer("Some lite application");
    claims.setExpirationTimeMinutesInTheFuture(10);
    claims.setGeneratedJwtId();
    claims.setIssuedAtToNow();
    claims.setNotBeforeMinutesInThePast(2);
    claims.setSubject(subject);
    claims.setClaim("email","example@example.com");
    claims.setClaim("fullName","Mr Test");

    JsonWebSignature jws = new JsonWebSignature();
    jws.setPayload(claims.toJson());
    jws.setKey(new HmacKey(jwtSharedSecret.getBytes()));
    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
    jws.setHeader(HeaderParameterNames.TYPE, "JWT");

    String jwt;
    try {
      jwt = jws.getCompactSerialization();
    } catch (JoseException e) {
      throw new RuntimeException(e);
    }

    return jwt;
  }
}
