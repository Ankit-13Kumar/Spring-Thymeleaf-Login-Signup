package com.becoder.config;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//using property inbuilt
@Configuration //Anotation imp
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
//override all properties
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/");
        } else if (roles.contains("ROLE_TEACHER")) {
            response.sendRedirect("/teacher/");
        } else {
            response.sendRedirect("/user/");
        }

    }

}
