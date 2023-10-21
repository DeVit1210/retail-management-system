package by.bsuir.retail.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticationResponse {
    private String authenticationToken;
}
