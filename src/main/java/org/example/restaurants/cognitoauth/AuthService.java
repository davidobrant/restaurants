package org.example.restaurants.cognitoauth;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//Användarinloggning
@Service
public class AuthService {

    @Autowired
    private CognitoProperties cognitoProperties;

    @Autowired
    private AWSCognitoIdentityProvider cognitoClient;

    private final String USER_POOL_ID = "user_pool_id";
    private final String CLIENT_ID = "app_client_id";

    public String authenticate(String username, String password) {
        AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                .withUserPoolId(USER_POOL_ID)
                .withClientId(CLIENT_ID)
                .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .addAuthParametersEntry("USERNAME", username)
                .addAuthParametersEntry("PASSWORD", password);

        AdminInitiateAuthResult authResult = cognitoClient.adminInitiateAuth(authRequest);
        return authResult.getAuthenticationResult().getIdToken();
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        AdminCreateUserRequest createUserRequest = new AdminCreateUserRequest()
                .withUserPoolId(USER_POOL_ID)
                .withUsername(username)
                .withTemporaryPassword(password)
                .withUserAttributes(
                        new AttributeType().withName("email").withValue(email),
                        new AttributeType().withName("email_verified").withValue("true"));

        cognitoClient.adminCreateUser(createUserRequest);
        return "User created successfully";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                .withUserPoolId(USER_POOL_ID)
                .withClientId(CLIENT_ID)
                .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .addAuthParametersEntry("USERNAME", username)
                .addAuthParametersEntry("PASSWORD", password);

        AdminInitiateAuthResult authResponse = cognitoClient.adminInitiateAuth(authRequest);
        return authResponse.getAuthenticationResult().getAccessToken();
    }
}
