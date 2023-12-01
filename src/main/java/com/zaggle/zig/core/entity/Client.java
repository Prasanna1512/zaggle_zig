package com.zaggle.zig.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Proxy(lazy = false)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;
    @Column(columnDefinition = "integer default 1")
    private int status;
    private String description;
    private String clientSecret;
    private String associatedData;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String signatureSecret;
    @JsonIgnoreProperties("client")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClientApp> clientApps;

    public Client(ClientApp... clientApps) {
        for (ClientApp clientApp : clientApps) clientApp.setClient(this);
        this.clientApps = Stream.of(clientApps).collect(Collectors.toSet());
    }
}
