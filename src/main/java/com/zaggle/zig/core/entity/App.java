package com.zaggle.zig.core.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Proxy(lazy = false)
public class App {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "integer default 1")
    private int status;
    private String description;
    private String secretKey;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @JsonIgnoreProperties("app")
    @OneToMany(mappedBy = "app", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ClientApp> clientApps = new HashSet<>();

    @JsonIgnoreProperties("app")
    @OneToMany(mappedBy = "app", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<AppApi> appApis = new HashSet<>();

    @JsonIgnoreProperties("app")
    @OneToMany(mappedBy = "app", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<AppWebhook> appWebhooks = new HashSet<>();
}
