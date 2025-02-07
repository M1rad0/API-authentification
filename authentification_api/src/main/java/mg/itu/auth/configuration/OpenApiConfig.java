package mg.itu.auth.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Authentification API") // Customize the title
                        .version("1.0.0") // Customize the version
                        .description("API d'implémentation de système d'authentification dans une application") // Customize the description
                        .contact(new Contact()
                                .name("ETU 2741 - 1647 - 2515 - 2480")
                                .email("testapi1532@gmail.com")
                                .url(""))
                        .license(new License()
                                .name("ITU")
                                .url("")));
    }
}