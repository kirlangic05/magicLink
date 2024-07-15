package com.magic3.magic3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usera")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Usera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true, length = 20)
    private String username;
    @Column(nullable = false,unique = true,length = 45)
    private String email;
    @Column(nullable = false, length = 64)
    private String password;
    private String token;


}
