package portfolio.dauntless;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class RequestHandler {

    @Bean(name = "entryPoint")
    public Function<String, String> entryPoint() {
        return String::toUpperCase;
    }

}
