package com.foodsystem.security;

import com.foodsystem.service.impl.CustomUserDetailsService;
import com.foodsystem.service.impl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter
{

    @Autowired
    JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token=null;
        String userEmail =null;
        if(header!=null && header.startsWith("Bearer "))
        {
            token=header.substring(7);
          userEmail=jwtService.extractUserEmail(token);
        }
        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails=context.getBean(CustomUserDetailsService.class).loadUserByUsername(userEmail);
         if(jwtService.validateToken(token,userDetails))
         {
             UsernamePasswordAuthenticationToken authenticationToken=
                     new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
              authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authenticationToken);
         }

        }
        filterChain.doFilter(request,response);
    }
}
