package booking_app_team_2.bookie.util;

import static org.junit.jupiter.api.Assertions.*;

import booking_app_team_2.bookie.domain.User;
import booking_app_team_2.bookie.domain.UserRole;
import booking_app_team_2.bookie.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TokenUtilsTest {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @DisplayName("Test generateToken when user is valid")
    public void testGenerateTokenWhenUserIsValid() {
        User user =
                userRepository
                        .save(
                                new User(
                                        "test",
                                        "test",
                                        "test",
                                        "test",
                                        "test",
                                        "test",
                                        UserRole.Guest
                                )
                        );

        String token = tokenUtils.generateToken(user);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("Test generateToken when user is not valid")
    public void testGenerateTokenWhenUserIsInvalid() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> tokenUtils.generateToken(new User()));
        assertEquals(
                "Cannot invoke \"booking_app_team_2.bookie.domain.UserRole.toString()\" because the return " +
                        "value of \"booking_app_team_2.bookie.domain.User.getRole()\" is null",
                exception.getMessage()
        );
    }

    @Test
    @Order(3)
    @DisplayName("Test getAuthorizationHeaderFromHeader when authorization header is not null")
    public void testGetAuthorizationHeaderFromHeaderWhenAuthorisationHeaderIsNotNull() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "test");

        assertNotNull(tokenUtils.getAuthorizationHeaderFromHeader(request));
    }

    @Test
    @Order(4)
    @DisplayName("Test getAuthorizationHeaderFromHeader when authorization header is null")
    public void testGetAuthorizationHeaderFromHeaderWhenAuthorizationHeaderIsNull() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        assertNull(tokenUtils.getAuthorizationHeaderFromHeader(request));
    }

    @Test
    @Order(5)
    @DisplayName("Test getToken when authorization header value starts with Bearer ")
    public void testGetTokenWhenAuthorizationHeaderValueStartsWithBearer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer test");

        String token = tokenUtils.getToken(request);
        assertNotNull(token);
        assertEquals("test", token);
    }

    @Test
    @Order(6)
    @DisplayName("Test getToken when authorization header value does not start with Bearer ")
    public void testGetTokenWhenAuthorizationHeaderValueDoesNotStartWithBearer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "test");

        assertNull(tokenUtils.getToken(request));
    }

    @Test
    @Order(7)
    @DisplayName("Test getToken when authorization header is null")
    public void testGetTokenWhenAuthorizationHeaderIsNull() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        assertNull(tokenUtils.getToken(request));
    }

    @Test
    @Order(8)
    @DisplayName("Test getIdFromToken when token is invalid")
    public void testGetIdFromTokenWhenTokenIsNull() {
        assertNull(tokenUtils.getIdFromToken("test"));
    }

    @Test
    @Order(9)
    @DisplayName("Test getIdFromToken when token is valid")
    public void testGetIdFromTokenWhenTokenIsValid() {
        User user =
                userRepository
                        .save(
                                new User(
                                        "test1",
                                        "test",
                                        "test",
                                        "test",
                                        "test",
                                        "test",
                                        UserRole.Guest
                                )
                        );

        assertNotNull(tokenUtils.getIdFromToken(tokenUtils.generateToken(user)));
    }
}
