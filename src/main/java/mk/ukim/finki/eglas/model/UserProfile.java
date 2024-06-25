package mk.ukim.finki.eglas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "korisnicki_profil")
public class UserProfile implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isSuperAdmin)
            return List.of(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_COMMITTEE"));
        if (isAdmin)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_COMMITTEE"));
        if (isCommittee)
            return List.of(new SimpleGrantedAuthority("ROLE_COMMITTEE"));
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return userName;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kp_id")
    Long id;
    @Column(name = "kp_korisnicko_ime")
    String userName;
    @Column(name = "kp_lozinka")
    String password;
    @Column(name = "kp_uloga")
    String role;
    @OneToOne
    @JoinColumn(name = "g_id")
    Citizen citizen;
    Boolean isCommittee;
    Boolean isAdmin;
    Boolean isSuperAdmin;
}
