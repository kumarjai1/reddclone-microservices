package com.example.apigateway.config;

import com.example.apigateway.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@ComponentScan("com.example")
public class SecurityConfig extends WebSecurityConfigurerAdapter{

  @Autowired
  CustomUserService userService;

  @Bean("encoder")
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  private JwtRequestFilter jwtRequestFilter;


  //TODO: cors issue for integration resolved by below
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/users/signup/**", "/users/login/**", "/posts/list", "/comments/list", "/comments/{\\d}",
                    "/users/hello", "/comments/hello", "/posts/hello").permitAll()
            .antMatchers("/users/**", "/posts/**", "/comments/**").authenticated()
//        .antMatchers("/role/**").hasRole("DBA")
            .and()
            .httpBasic()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService);
  }
}
