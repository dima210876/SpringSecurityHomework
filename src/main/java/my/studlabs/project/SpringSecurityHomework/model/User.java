package my.studlabs.project.SpringSecurityHomework.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotBlank
    @Column(name = "email")
    private String email;

    @NotNull
    @NotBlank
    @Column(name = "password")
    private String password;

    @NotNull
    @NotBlank
    @Column(name = "first_name")
    private String firstname;

    @NotNull
    @NotBlank
    @Column(name = "role")
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        SimpleGrantedAuthority authority = switch (getRole())
        {
            case "admin" -> new SimpleGrantedAuthority("ROLE_ADMIN");
            default -> new SimpleGrantedAuthority("ROLE_USER");
        };
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public int getId() { return id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public User() {}

    public User(int id, String email, String password, String firstname, String role)
    {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.role = role;
    }

    public User(String email, String password, String firstname, String role)
    {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.role = role;
    }

    public User(User user)
    {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstname = user.getFirstname();
        this.role = user.getRole();
    }

    @Override
    public String toString()
    {
        return firstname + " (email: " + email + ", role: " + role + ")";
    }
}
