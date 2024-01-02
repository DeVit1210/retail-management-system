package by.bsuir.retail.response.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticationResponse {
    private String authenticationToken;
    private String authority;
}
