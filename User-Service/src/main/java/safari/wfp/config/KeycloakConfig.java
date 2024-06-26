package safari.wfp.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    @Value("${keycloak.url}")
    private String serverUrl;

    @Value("${keycloak.client.id}")
    private String clientId;

    @Value("${keycloak.client.secret}")
    private String clientSecret;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    @Value("${keycloak.realm}")
    private String realm;

    @Bean
    public Keycloak getAdminKeycloakUser() {
        return KeycloakBuilder.builder().serverUrl(serverUrl)
                .grantType(OAuth2Constants.PASSWORD)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
    }
    @Bean
    public RealmResource getRealmResource() {
        return getAdminKeycloakUser().realm(realm);
    }

    @Bean
    public UsersResource usersResource(){
        return getRealmResource().users();
    }
    @Bean
    public CredentialRepresentation credentialRepresentation(){
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType("password");
        return passwordCred;
    }
    @Bean
    public UserRepresentation userRepresentation(){
        return new UserRepresentation();
    }
}
