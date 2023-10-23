package by.bsuir.retail.service.auth;

import by.bsuir.retail.entity.users.Role;
import by.bsuir.retail.mapper.RegistrationRequestMapper;
import by.bsuir.retail.request.auth.RegistrationRequest;
import by.bsuir.retail.response.auth.RegistrationResponse;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.security.simple.AppUserService;
import by.bsuir.retail.service.exception.EmailAlreadyTakenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;
    private final RegistrationRequestMapper mapper;
    private final ResponseBuilder responseBuilder;
    public ResponseEntity<RegistrationResponse> register(RegistrationRequest request) {
        boolean userExists = appUserService.isUserExists(request);
        if (userExists) {
            UserDetails createdUser = appUserService.saveUser(request, mapper);
            return responseBuilder.buildRegistrationResponse(createdUser);
        } else throw new EmailAlreadyTakenException();
    }
}
