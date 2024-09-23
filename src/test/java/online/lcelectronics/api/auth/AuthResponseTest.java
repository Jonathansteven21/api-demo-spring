package online.lcelectronics.api.auth;

import online.demo.api.auth.AuthResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthResponseTest {

    /**
     * Test for the no-args constructor.
     * Ensures that a new instance of AuthResponse is created with a null value for the token.
     */
    @Test
    void testNoArgsConstructor() {
        AuthResponse authResponse = new AuthResponse();
        assertThat(authResponse).isNotNull();
        assertThat(authResponse.getToken()).isNull();
    }

    /**
     * Test for the all-args constructor.
     * Ensures that a new instance of AuthResponse is created with the specified token.
     */
    @Test
    void testAllArgsConstructor() {
        AuthResponse authResponse = new AuthResponse("sampleToken","ADMIN",100);
        assertThat(authResponse.getToken()).isEqualTo("sampleToken");
    }

    /**
     * Test for the builder pattern.
     * Ensures that a new instance of AuthResponse is created with the specified token using the builder.
     */
    @Test
    void testBuilder() {
        AuthResponse authResponse = AuthResponse.builder()
                .token("sampleToken")
                .build();
        assertThat(authResponse.getToken()).isEqualTo("sampleToken");
    }

    /**
     * Test for the setters and getters.
     * Ensures that the token field can be set and retrieved correctly.
     */
    @Test
    void testSettersAndGetters() {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken("sampleToken");
        assertThat(authResponse.getToken()).isEqualTo("sampleToken");
    }
}
