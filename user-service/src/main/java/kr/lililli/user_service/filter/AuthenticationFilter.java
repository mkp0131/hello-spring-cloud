package kr.lililli.user_service.filter;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.lililli.user_service.vo.RequestLogin;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();


    // 인증 시도
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        // TODO Auto-generated method stub
        log.info("SPRING_SECURITY_FORM_PASSWORD_KEY");

        try {
            RequestLogin creds = objectMapper.readValue(request.getReader(), RequestLogin.class);

            // 인증 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(),
                            new ArrayList<>());

            // 인증 토큰을 이용해서 인증 처리
            Authentication authentication =
                    getAuthenticationManager().authenticate(authenticationToken);

            return authentication;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse authentication request body", e);
        }
    }


    // 인증 성공시 처리 로직
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        super.successfulAuthentication(request, response, chain, authResult);
    }



}
