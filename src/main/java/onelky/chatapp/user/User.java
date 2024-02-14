package onelky.chatapp.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user",uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})} )
public class User implements UserDetails {
    @Id
    @GeneratedValue
    Integer id;
    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    String email;

    @Enumerated(EnumType.STRING)
    Provider provider;

    @Column
    String profilePictureUrl;

    @Column
    String profilePicturePublicId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
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
