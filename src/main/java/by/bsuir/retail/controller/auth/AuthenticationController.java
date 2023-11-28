package by.bsuir.retail.controller.auth;


import by.bsuir.retail.request.auth.AuthenticationRequest;
import by.bsuir.retail.request.auth.RegistrationRequest;
import by.bsuir.retail.response.auth.AuthenticationResponse;
import by.bsuir.retail.response.auth.RegistrationResponse;
import by.bsuir.retail.service.auth.AuthenticationService;
import by.bsuir.retail.service.auth.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return authenticationService.login(request);
    }
}
