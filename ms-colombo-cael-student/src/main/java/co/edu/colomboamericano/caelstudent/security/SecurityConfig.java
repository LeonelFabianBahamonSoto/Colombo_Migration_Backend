package co.edu.colomboamericano.caelstudent.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import co.edu.colomboamericano.caelstudent.exception.AuthException;
import co.edu.colomboamericano.caelstudent.security.jwt.JwtAuthorizationFilter;

import org.springframework.security.authentication.AuthenticationManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig
{
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        AuthenticationManagerBuilder auth = httpSecurity.getSharedObject(
                AuthenticationManagerBuilder.class
        );
        
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
        AuthenticationManager authenticationManager = auth.build();
        httpSecurity.cors();
        httpSecurity.csrf().disable();
        httpSecurity.authenticationManager(authenticationManager);
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        httpSecurity.authorizeHttpRequests()
        .antMatchers("/api/authentication/sign-in","/api/authentication/sign-up").permitAll().antMatchers("/v1/student/**").authenticated();
        
        //Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthException();
    }
}