package me.andrusha.vpnpayment.conf;

import me.andrusha.vpnpayment.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception  {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/registration").hasRole("USER")
                .antMatchers("/auth/role").hasRole("USER")
                .antMatchers("/auth/login").permitAll()

                .antMatchers("/shop/products").permitAll()
                .antMatchers(HttpMethod.GET, "/shop/product").permitAll()
                .antMatchers(HttpMethod.POST, "/shop/product").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/shop/product").hasRole("USER")
                .antMatchers(HttpMethod.PATCH, "/shop/product").hasRole("USER")

                .antMatchers("/shop/duration").hasRole("USER")

                .antMatchers("/shop/category").hasRole("USER")

                .antMatchers("/shop/promocode").hasRole("USER")

                .antMatchers("/shop/durations").permitAll()

                .antMatchers("/shop/categories").permitAll()


                .antMatchers("/images/**").permitAll()


                .antMatchers("/payment/**").permitAll()
                .antMatchers("/webhook/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}