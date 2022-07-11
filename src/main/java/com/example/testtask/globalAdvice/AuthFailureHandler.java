package com.example.testtask.globalAdvice;

import com.example.testtask.entity.Attempts;
import com.example.testtask.service.AttemptsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final AttemptsService attemptsService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        List<Attempts> attemptsList;
        Attempts attempts;
        Date date;
        String idSession = request.getSession().getId();

        attemptsList = attemptsService.getAllAttempts();
        if (attemptsList.size() == 0) {
            date = new Date();
            attempts = new Attempts();
            attempts.setTime(date.getTime())
                    .setCountAttempts(1)
                    .setNumberSession(idSession);
            attemptsService.saveAttempts(attempts);
        } else {
            Integer cntAtt;
            List<Attempts> attemptsBySessionId = attemptsList.stream()
                    .filter(p -> p.getNumberSession().equals(idSession))
                    .toList();
            if (attemptsBySessionId.isEmpty()){
                date = new Date();
                attempts = new Attempts();
                attempts.setTime(date.getTime())
                        .setCountAttempts(1)
                        .setNumberSession(idSession);
                attemptsService.saveAttempts(attempts);
                getRedirectStrategy().sendRedirect(request, response, "/unauthorized");
                return;
            }
            attempts = attemptsBySessionId.get(0);
            cntAtt = attempts.getCountAttempts();
            if (cntAtt >= 10){
                getRedirectStrategy().sendRedirect(request, response, "/authorization-limit");
                return;
            }
            cntAtt++;
            attempts.setCountAttempts(cntAtt);
            attemptsService.updateAttempts(attempts, attempts.getId());
            getRedirectStrategy().sendRedirect(request, response, "/unauthorized");
        }
    }
}
