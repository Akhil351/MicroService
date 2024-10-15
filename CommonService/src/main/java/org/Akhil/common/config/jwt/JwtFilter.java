package org.Akhil.common.config.jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.Akhil.common.config.userDetails.CustomerDetailsService;
import org.Akhil.common.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomerDetailsService customerDetailsService;

    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwtToken=this.parseJwt(request);
        if(StringUtils.hasText(jwtToken)){
            try {
                if(jwtService.validateToken(jwtToken)){
                    String email=jwtService.getEmail(jwtToken);
                    UserDetails userDetails=customerDetailsService.loadUserByUsername(email);
                    if(userDetails!=null){
                        Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                    else{
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                }
                else{
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                // Create a simple JSON message
                String errorResponse = "{\"error\": \"Unauthorized\", \"message\": \"" + e.getMessage() + "\"}";
                // Write the JSON response to the output
                response.getWriter().write(errorResponse);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }
    private String parseJwt(HttpServletRequest request){
        String header=request.getHeader(JwtUtils.JWT_HEADER);
        if(StringUtils.hasText(header) && header.startsWith(JwtUtils.JWT_BEARER)){
            return header.substring(7);
        }
        return null;
    }
}


