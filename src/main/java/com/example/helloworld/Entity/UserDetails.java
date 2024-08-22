package com.example.helloworld.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userName;
    private String userPassword;

}
