package org.Akhil.common.model;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    @NaturalId
    private String email;
    private String password;
    private String phoneNumber;
}
