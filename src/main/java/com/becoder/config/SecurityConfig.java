package com.becoder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//extends web security for security reason
// only supports 2.6.5 spring version after that depreciate
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Add custome before name
    @Autowired
    private AuthenticationSuccessHandler CustomSuccessHandler;

  /*  @Autowired
    private userDetailsService userDetailsService;*/
    //Use Directly Bean Here
    @Bean
    public UserDetailsService getUserDetailsService() {
        return new UserDetailsServiceImpl();
    }

    //Encrypt and Decrypt Password
    //Bean when run create methods and get user objects
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getDaoAuthProvider());
    }
//configure httpsecurity overide by right click configure security
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/admin/**")
                .hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                //Added for admin
                .antMatchers("/teacher/**").access("hasRole('TEACHER')")
                //
                .antMatchers("/**").permitAll().and()
                .formLogin()
                .loginPage("/signin").loginProcessingUrl("/login")
               /* .defaultSuccessUrl("/user/")*/ //No use in admin
                .successHandler(CustomSuccessHandler)
                .and().csrf().disable();

    }

}