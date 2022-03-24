package com.company.data.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ToString.Exclude
    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();
}
