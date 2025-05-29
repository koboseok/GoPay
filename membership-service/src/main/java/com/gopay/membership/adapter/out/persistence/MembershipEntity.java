package com.gopay.membership.adapter.out.persistence;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "membership")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MembershipEntity {

    @Id
    @GeneratedValue
    private Long membershipId;
    private String name;
    private String email;
    private String address;
    private boolean isValid;
    private boolean isCorp;


    public MembershipEntity(String name, String email, String address, boolean isValid, boolean isCorp) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;
    }
}
