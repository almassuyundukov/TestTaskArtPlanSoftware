package com.example.testtask.globalAdvice;

import com.example.testtask.entity.Attempts;
import com.example.testtask.service.AttemptsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final AttemptsService attemptsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        Attempts attempts = new Attempts();
        Date date = new Date();
        Attempts attemptsBySessionId = attemptsService.getAttemptsBySessionId(request.getSession().getId());
        if (attemptsBySessionId == null){
            attempts.setNumberSession(request.getSession().getId())
                    .setCountAttempts(0)
                    .setTime(date.getTime());
            attemptsService.saveAttempts(attempts);
            String username = authentication.getPrincipal().toString();
            getRedirectStrategy().sendRedirect(request,response, "/user/" + username);
            return;
        }
        if (attemptsBySessionId.getCountAttempts() > 10 && date.getTime() - attempts.getTime() < 60*60*1000) {
            getRedirectStrategy().sendRedirect(request, response, "/authorization-limit");
            return;
        }
        attemptsBySessionId.setCountAttempts(0);
        Integer id = attemptsBySessionId.getId();
        attemptsService.updateAttempts(attemptsBySessionId, id);
        String username = authentication.getPrincipal().toString();
        getRedirectStrategy().sendRedirect(request, response, "/user/" + username);

    }
}
