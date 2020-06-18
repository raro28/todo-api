package mx.ekthor.todo.rest.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
              .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/lists", "/lists/*", "/lists/*/tasks", "/notes/*", "/tasks/*", "/tasks/*/notes")
                  .hasAuthority("SCOPE_read")
                .antMatchers(HttpMethod.POST, "/lists", "/lists/*/tasks", "/tasks/*/notes")
                  .hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.PUT,  "/lists/*", "/notes/*", "/tasks/*")
                  .hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.DELETE,  "/lists/*", "/notes/*", "/tasks/*")
                  .hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.GET, "/swagger-ui.html", "/swagger-ui/*", "/v3/api-docs**/**")
                    .permitAll()
                .anyRequest()
                  .authenticated()
            .and()
              .oauth2ResourceServer()
                .jwt();
    }
}