package utn.project.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import utn.project.domain.enums.UserType;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;

@Service
public class SessionFilter extends OncePerRequestFilter{

    @Autowired
    private SessionManager sessionManager;

    private static final String userTypeCustomer = "CUSTOMER";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServerException, IOException, ServletException {

        String sessionToken = request.getHeader("Authorization");
        Session session = sessionManager.getSession(sessionToken);

        if (null != session) {

            if (session.getLoggedUser().getUserType() == UserType.CUSTOMER) {
                filterChain.doFilter(request, response);
            }
            else {
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        }
        else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }
}