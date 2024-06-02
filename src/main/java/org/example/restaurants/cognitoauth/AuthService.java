package org.example.restaurants.cognitoauth;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AWSCognitoIdentityProvider cognitoClient;

    private final String USER_POOL_ID = "your_user_pool_id";
    private final String CLIENT_ID = "your_app_client_id";

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
}
