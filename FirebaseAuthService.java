import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
/**
 * Beschreiben Sie hier die Klasse FirebaseAuthService.
 * 
 * @author Thiébaud Reimann 
 * @version 1.0
 */

public class FirebaseAuthService {
    private static final String API_KEY = "AIzaSyDdEpQxLkZwaWeMDuqaV0B2xpQQXr1DW54";
    private static final String FIREBASE_AUTH_URL =
        "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY;
    private static final MediaType JSON =
        MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    // 1. Request-Body
    static class SignInRequest {
        String email, password;
        boolean returnSecureToken = true;
        SignInRequest(String email, String pwd) {
            this.email = email;
            this.password = pwd;
        }
    }

    // 2. Response-Model
    public static class SignInResponse {
        public String idToken;       // das Auth-Token
        public String localId;       // die User-ID
        public String email;
    }

    // 3. Error-Wrapper
    static class ErrorBody {
        static class Error {
            String message;
        }
        Error error;
    }

    /** Meldet den Nutzer per E-Mail/Passwort an. */
    public SignInResponse signIn(String email, String password) throws IOException {
        SignInRequest req = new SignInRequest(email, password);
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));

        Request request = new Request.Builder()
            .url(FIREBASE_AUTH_URL)
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            if (response.isSuccessful()) {
                return gson.fromJson(json, SignInResponse.class);
            } else {
                ErrorBody eb = gson.fromJson(json, ErrorBody.class);
                throw new IOException(eb.error.message);
            }
        }
    }
}
