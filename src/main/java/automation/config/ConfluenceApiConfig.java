package automation.config;


import com.github.crob1140.confluence.ConfluenceClient;
import com.github.crob1140.confluence.auth.AuthMethod;
import com.github.crob1140.confluence.auth.BasicAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@Configuration
public class ConfluenceApiConfig {

    @Bean
    public ConfluenceClient confluenceClient() {
        WebTarget wikiTarget = ClientBuilder.newClient().target("https://team-178154211245.atlassian.net/wiki");
        AuthMethod basicAuth = new BasicAuth("x-camunda@yandex.ru", "C9ra8IGGanynC2zF1b9o34C2");
        return new ConfluenceClient(wikiTarget, basicAuth);
    }
}
