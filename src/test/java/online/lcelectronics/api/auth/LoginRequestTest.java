package online.lcelectronics.api.auth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginRequestTest {

    /**
     * Test for the no-args constructor.
     * Ensures that a new instance of LoginRequest is created with null values for username and password.
     */
    @Test
    void testNoArgsConstructor() {
        LoginRequest loginRequest = new LoginRequest();
        assertThat(loginRequest).isNotNull();
        assertThat(loginRequest.getUsername()).isNull();
        assertThat(loginRequest.getPassword()).isNull();
    }

    /**
     * Test for the all-args constructor.
     * Ensures that a new instance of LoginRequest is created with the specified username and password.
     */
    @Test
    void testAllArgsConstructor() {
        LoginRequest loginRequest = new LoginRequest("user", "pass");
        assertThat(loginRequest.getUsername()).isEqualTo("user");
        assertThat(loginRequest.getPassword()).isEqualTo("pass");
    }

    /**
     * Test for the builder pattern.
     * Ensures that a new instance of LoginRequest is created with the specified username and password using the builder.
     */
    @Test
    void testBuilder() {
        LoginRequest loginRequest = LoginRequest.builder()
                .username("user")
                .password("pass")
                .build();
        assertThat(loginRequest.getUsername()).isEqualTo("user");
        assertThat(loginRequest.getPassword()).isEqualTo("pass");
    }

    /**
     * Test for the setters and getters.
     * Ensures that the username and password fields can be set and retrieved correctly.
     */
    @Test
    void testSettersAndGetters() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("pass");
        assertThat(loginRequest.getUsername()).isEqualTo("user");
        assertThat(loginRequest.getPassword()).isEqualTo("pass");
    }
}
