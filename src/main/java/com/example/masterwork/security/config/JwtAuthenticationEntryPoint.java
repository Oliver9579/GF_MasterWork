package com.example.masterwork.security.config;

import com.example.masterwork.errorhandling.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException authException) throws IOException {

    final String requestTokenHeader = request.getHeader("Authorization");


    if (requestTokenHeader == null || requestTokenHeader == "") {

      ObjectWriter objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();

      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getOutputStream()
              .println(objectMapper.writeValueAsString(new ErrorMessage("No authentication token is provided!")));

    }
  }

}