package portfolio.dauntless;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI openAPI(
            @Value("${swagger.server.url}") String server,
            @Value("${spring.application.name}") String appName,
            @Value("${springdoc.version}") String version
    ) {
        return new OpenAPI()
        .servers(List.of(new Server().url(server)))
                .info(new Info().title(appName + " Docs").version(version));

    }
}
