package divacademy.homemate.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity(name = "_user")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "_user_seq")
    @SequenceGenerator(name = "_user_seq",sequenceName = "_user_seq",initialValue = 100,allocationSize = 50)
    @Id
    private long id;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String password;
    private String lastLoginIp;
    private boolean enabled;
    @ManyToOne
    private Role role;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TableDetail tableDetail;
    @OneToOne(cascade = CascadeType.PERSIST)
    private UserDetail userDetail;

    public User(String email,String phoneNumber, String password, Role role) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.lastLoginIp = getLoginIp();
        this.role = role;
        this.tableDetail = TableDetail.of();
    }

    public User() {
        this.tableDetail = TableDetail.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<Permission> permissions = new ArrayList<>(role.getPermissions());

        permissions.add(new Permission(role.getName()));

        return permissions;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }
    private  String getLoginIp(){
        return ((ServletRequestAttributes) (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest().getRemoteHost();
    }
    public void updateLoginIp(){
        this.lastLoginIp = getLoginIp();
    }
}
