package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "\"user\"")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
}
