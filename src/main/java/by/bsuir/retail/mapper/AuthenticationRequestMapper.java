package by.bsuir.retail.mapper;

import by.bsuir.retail.request.auth.AuthenticationRequest;
import by.bsuir.retail.security.simple.authtoken.CustomUsernamePasswordAuthenticationToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthenticationRequestMapper  {
    @Mapping(target = "principal", source = "username")
    @Mapping(target = "credentials", source = "password")
    CustomUsernamePasswordAuthenticationToken mapToAuthentication(AuthenticationRequest authenticationRequest);
}
