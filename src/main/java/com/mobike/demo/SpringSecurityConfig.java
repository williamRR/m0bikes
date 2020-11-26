package com.mobike.demo;

import com.mobike.demo.auth.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


  @Autowired
  private LoginSuccessHandler successHandler;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private UserDetailsService usuarioService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/", "/css/**", "/js/**", "/images/**", "/home", "/register", "/404").permitAll()
            .antMatchers("/user/**").hasAnyRole("USER")
            .antMatchers("/admin/**").hasAnyRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .successHandler(successHandler)
            .loginPage("/login")
            .permitAll()
            .and()
            .logout().permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/error_403");
  }

  @Autowired
  public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
    builder.userDetailsService(usuarioService)
            .passwordEncoder(passwordEncoder);


//            jdbcAuthentication()
//            .dataSource(dataSource)
//            .passwordEncoder(passwordEncoder)
//            .usersByUsernameQuery("select username, password, enabled from users where username=?")
//            .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id = u.id) where u.username = ?");


//    UserBuilder users = User.builder().passwordEncoder(password -> {
//      return encoder.encode(password);
//    });
//
//    builder.inMemoryAuthentication()
//            .withUser(users.username("admin").password("admin").roles("ADMIN"))
//            .withUser(users.username("user").password("user").roles("USER"));
  }

}
