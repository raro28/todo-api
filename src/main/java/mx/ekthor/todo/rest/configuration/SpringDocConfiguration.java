package mx.ekthor.todo.rest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@SecurityScheme(
     name = "oAuth2",
     type = SecuritySchemeType.OAUTH2,
     flows = @OAuthFlows(
         implicit = @OAuthFlow(
            authorizationUrl = "http://mumei:8880/auth/realms/todolist/protocol/openid-connect/auth",
            scopes = {
               @OAuthScope(name = "read", description = "read access"),
               @OAuthScope(name = "write", description = "Write access")
            }
         ),
         authorizationCode = @OAuthFlow(
            authorizationUrl = "http://mumei:8880/auth/realms/todolist/protocol/openid-connect/auth",
            tokenUrl = "http://mumei:8880/auth/realms/todolist/protocol/openid-connect/token",
            scopes = {
               @OAuthScope(name = "read", description = "read access"),
               @OAuthScope(name = "write", description = "Write access")
            }
         ),
         password = @OAuthFlow(
            tokenUrl = "http://mumei:8880/auth/realms/todolist/protocol/openid-connect/token",
            scopes = {
               @OAuthScope(name = "read", description = "read access"),
               @OAuthScope(name = "write", description = "Write access")
            }
         )/*,
         clientCredentials = @OAuthFlow(
            tokenUrl = "http://mumei:8880/auth/realms/todolist/protocol/openid-connect/token",
            scopes = {
               @OAuthScope(name = "read", description = "read access"),
               @OAuthScope(name = "write", description = "Write access")
            }
         )*/
     )
)
@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Todo List API")
                .version("0.0.0")
                .description("Defines the endpoints and operations that manage todo lists and their related tasks and notes. See [Github repository](https://github.com/raro28/todo-api).")
                .contact(new Contact()
                    .name("Héctor Díaz")
                    .url("https://github.com/raro28")
                )
            )
            .addServersItem(new Server()
                .url("http://mumei:3001/v0")
                .description("mockoon server")
            )
            .addServersItem(new Server()
                .url("http://mumei:8080")
                .description("spring-boot server")
            );
    }
}
