import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
/**
 * Beschreiben Sie hier die Klasse FirebaseAuthService.
 * 
 * @author Thiébaud Reimann 
 * @version 1.1
 */

public class FirebaseAuthService {
    private static final String API_KEY = "AIzaSyDdEpQxLkZwaWeMDuqaV0B2xpQQXr1DW54";
    private static final String FIREBASE_AUTH_URL =
        "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY;
    private static final String FIREBASE_RESET_PASSWORD_URL =
        "https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=" + API_KEY;
    private static final String FIREBASE_SIGNUP_URL =
        "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + API_KEY;
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

    // Request-Body für Password Reset
    static class PasswordResetRequest {
        String requestType = "PASSWORD_RESET";
        String email;
        
        PasswordResetRequest(String email) {
            this.email = email;
        }
    }

    // Request-Body für SignUp
    static class SignUpRequest {
        String email, password;
        boolean returnSecureToken = true;
        
        SignUpRequest(String email, String pwd) {
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

    // Response-Model für Password Reset
    public static class PasswordResetResponse {
        public String email;
    }

    // Response-Model für SignUp
    public static class SignUpResponse {
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

    /** Sendet eine Passwort-Reset-E-Mail an die angegebene E-Mail-Adresse. */
    public PasswordResetResponse resetPassword(String email) throws IOException {
        PasswordResetRequest req = new PasswordResetRequest(email);
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));

        Request request = new Request.Builder()
            .url(FIREBASE_RESET_PASSWORD_URL)
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            if (response.isSuccessful()) {
                return gson.fromJson(json, PasswordResetResponse.class);
            } else {
                ErrorBody eb = gson.fromJson(json, ErrorBody.class);
                throw new IOException(eb.error.message);
            }
        }
    }

    /** Registriert einen neuen Nutzer mit E-Mail und Passwort. */
    public SignUpResponse signUp(String email, String password) throws IOException {
        SignUpRequest req = new SignUpRequest(email, password);
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));

        Request request = new Request.Builder()
            .url(FIREBASE_SIGNUP_URL)
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            if (response.isSuccessful()) {
                return gson.fromJson(json, SignUpResponse.class);
            } else {
                ErrorBody eb = gson.fromJson(json, ErrorBody.class);
                throw new IOException(eb.error.message);
            }
        }
    }
}
