package com.example.masterwork.security;

import com.example.masterwork.security.config.JwtTokenUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class JwtTokenUtilTest {

  private JwtTokenUtil jwtTokenUtil;
  private String secretString;
  private SecretKey signingKey;
  private LinkedHashMap<String, Object> claims;

  @Before
  public void setup() {
    secretString = "eN9wb3PAoYX5PmRDfnf0EsizrMruiKwETwb3PAKwETw";
    jwtTokenUtil = new JwtTokenUtil();
    jwtTokenUtil.setSecretString(secretString);
    signingKey = Keys.hmacShaKeyFor(secretString.getBytes());
  }

  @Test
  public void createJwtsToken_should_returnAnEmptyString_when_nullIsGiven() {
    jwtTokenUtil.createJwtsToken(null);
  }

  @Test
  public void createJwtsToken_should_returnAnEmptyString_when_EmptyMapIsGiven() {
    jwtTokenUtil.createJwtsToken(new LinkedHashMap<>());
  }

  @Test
  public void createJwtsToken_should_produceCorrectSignature() {
    claims = new LinkedHashMap<>();
    claims.put("user", "Oli2");
    String myToken = jwtTokenUtil.createJwtsToken(claims);
    String myTokenSignature = myToken.substring(myToken.lastIndexOf('.') + 1);

    Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(myToken);
    assertEquals(jws.getSignature(), myTokenSignature);
  }

  @Test
  public void createJwtsToken_should_returnATokenWithGivenClaims() {
    claims = new LinkedHashMap<>();
    Long expirationDate = new Date(System.currentTimeMillis() + 3_600_000).getTime();
    claims.put("user", "Oli2");
    claims.put("userId", 2);
    claims.put("exp", expirationDate);
    String myToken = jwtTokenUtil.createJwtsToken(claims);

    Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .require("user", "Oli2")
                .require("userId", 2)
                .require("exp", expirationDate)
                .build()
                .parseClaimsJws(myToken);

    assertEquals(claims, jws.getBody());
  }

}