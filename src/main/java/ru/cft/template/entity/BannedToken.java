package ru.cft.template.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "banned_tokens")
public class BannedToken {
    @Id
    @Column(name = "token")
    private String token;

}