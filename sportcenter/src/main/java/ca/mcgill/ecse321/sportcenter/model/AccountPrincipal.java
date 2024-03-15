package ca.mcgill.ecse321.sportcenter.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountPrincipal implements UserDetails{
    private Account account;
    private Authority authority;

    public AccountPrincipal(Customer customer) {
        this.account = customer;
        setAuthority(Authority.ROLE_CUSTOMER);
    }

    public AccountPrincipal(Instructor instructor) {
        this.account = instructor;
        setAuthority(Authority.ROLE_INSTRUCTOR);
    }

    public AccountPrincipal(Owner owner) {
        this.account = owner;
        setAuthority(Authority.ROLE_OWNER);
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> list = new ArrayList<>();
        list.add(authority);
        return list;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
