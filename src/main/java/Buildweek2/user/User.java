package Buildweek2.user;

import Buildweek2.bill.Bill;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "UserBuilder")
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"createdAt", "password", "authorities", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String username;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @CreationTimestamp
    @Column(name = "creation_date")
    private Date createdAt;

    @OneToMany(mappedBy = "user")
    private Set<Bill> bills = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return null;
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

    public static class UserBuilder {
        Faker faker = new Faker(Locale.ITALY);
        private String name = faker.name().firstName();
        private String surname = faker.name().lastName();
        private String email = name + "." + surname + "@gmail.com";
        private String avatarUrl = "https://ui-avatars.com/api/?name=" + name + "+" + surname;
        private String password = faker.phoneNumber().cellPhone();
        private String username = faker.funnyName().name();
        private UserRole role = UserRole.USER;
    }
}
