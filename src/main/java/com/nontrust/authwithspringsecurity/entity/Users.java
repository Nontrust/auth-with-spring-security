package com.nontrust.authwithspringsecurity.entity;

// jakarta ≒ javax (EE4J)
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    @Id
    private Long idx;
    @Column
    private String email;
    @Column
    private String password;

}
