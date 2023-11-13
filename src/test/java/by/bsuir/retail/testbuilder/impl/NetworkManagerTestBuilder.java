package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.users.NetworkManager;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import org.junit.jupiter.api.Test;

@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class NetworkManagerTestBuilder implements TestBuilder<NetworkManager> {
    @With
    private String username = "username";
    @With
    private String password = "password";
    private boolean enabled = true;
    @With
    private String fullName = "fullName";
    @Override
    public NetworkManager build() {
        return NetworkManager.builder()
                .login(username)
                .password(password)
                .enabled(enabled)
                .fullName(fullName)
                .build();
    }
}
