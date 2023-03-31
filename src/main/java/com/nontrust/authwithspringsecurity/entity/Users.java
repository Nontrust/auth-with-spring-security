package com.nontrust.authwithspringsecurity.entity;

// jakarta ≒ javax (EE4J)
import com.nontrust.authwithspringsecurity.api.v1.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id
    private Long idx;
    @NotNull
    @Column
    private String email;
    @NotNull
    @Column
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @Column
    private List<Role> role = new ArrayList<>();


    public Users addRole(Role role){
        this.role.add(role);
        return this;
    }

    public boolean hasRole(Role role){
        return this.role.contains(role);
    }


}
