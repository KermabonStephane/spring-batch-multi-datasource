package org.demis27.example.output.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    private String id;
    private String firstname;
    private String lastname;
}
