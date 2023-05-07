package com.example.masterwork.security.config;

import com.example.masterwork.errorhandling.ErrorMessage;
import com.example.masterwork.exceptions.UserNotFoundException;
import com.example.masterwork.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
          throws ServletException, IOException {
    String jwtToken = getJwtToken(request.getHeader("Authorization"));
    if (jwtToken == null) {
      chain.doFilter(request, response);
      return;
    }
    String username = null;
    try {
      username = jwtTokenUtil.getUsernameFromToken(jwtToken);
    } catch (Exception e) {
      invalidTokenError(response);
      return;
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
            && !jwtTokenUtil.isTokenExpired(jwtToken)) {
      setupSecurityContext(username, jwtToken, request, response);
    }
    chain.doFilter(request, response);
  }

  private void setupSecurityContext(String username, String jwtToken, HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
    try {
      UserDetails userDetails = this.userService.getByUsername(username);
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails, jwtTokenUtil.getAllClaimsFromToken(jwtToken), userDetails.getAuthorities());
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    } catch (UserNotFoundException e) {
      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getOutputStream()
              .println(objectMapper.writeValueAsString(new ErrorMessage(e.getMessage())));
    }

  }

  private String getJwtToken(String requestTokenHeader) {
    if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) return null;
    return requestTokenHeader.substring(7);
  }

  private void invalidTokenError(HttpServletResponse response) {
    ObjectWriter objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
    response.setContentType("application/json");
    response.setStatus(403);
    try {
      response.getOutputStream()
              .println(objectMapper.writeValueAsString(new ErrorMessage("Authentication token is invalid!")));
    } catch (IOException e) {
      System.err.printf("Unable to send 'Authentication token is invalid!' error response.\n");
    }
  }

}