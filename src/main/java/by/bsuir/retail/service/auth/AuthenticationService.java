package by.bsuir.retail.service.auth;

import by.bsuir.retail.mapper.auth.AuthenticationRequestMapper;
import by.bsuir.retail.request.auth.AuthenticationRequest;
import by.bsuir.retail.response.auth.AuthenticationResponse;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationRequestMapper mapper;
    private final ResponseBuilder responseBuilder;
    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(mapper.mapToAuthentication(request));
        return responseBuilder.buildAuthenticationResponse(authentication);
    }
}
