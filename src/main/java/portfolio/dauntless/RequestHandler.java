package portfolio.dauntless;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.web.util.UriTemplate;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.function.Function;

@Configuration
public class RequestHandler {

    @Bean(name = "requestRouter")
    public Function<Flux<APIGatewayProxyRequestEvent>, Flux<APIGatewayProxyResponseEvent>> requestRouter(FunctionCatalog catalog) {

        return (flux) -> flux.map((request) -> {
            String resource = request.getResource();
            UriTemplate uriTemplate = new UriTemplate("/api/{resource}");
            Map<String, String> match = uriTemplate.match(resource);
            String functionName = match.get("resource");

            Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> function = catalog.lookup(functionName);

            return function.apply(request);
        });
    }

    @Bean(name = "uppercase")
    public Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> uppercase() {
        return (request) -> {
            APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
            response.setStatusCode(200);
            response.setBody(request.getBody().toUpperCase());
            return response;
        };
    }

    @RouterOperation(operation = @Operation(description = "Say hello", operationId = "hello", tags = "persons",
            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = APIGatewayProxyRequestEvent.class)))))
    @Bean(name = "hello")
    public Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> hello() {
        return (request) -> {
            APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
            response.setStatusCode(200);
            response.setBody("Hello fellows");
            return response;
        };
    }


    @Bean
    public MessageRoutingCallback customRouter() {
        return new MessageRoutingCallback() {
            @Override
            public String routingResult(Message<?> message) {
                return "requestRouter";
            }
        };
    }

}
