package ca.mcgill.ecse321.sportcenter.model;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    ROLE_CUSTOMER,
    ROLE_INSTRUCTOR,
    ROLE_OWNER;

    @Override
    public String getAuthority() {
        return name();
    }
}
