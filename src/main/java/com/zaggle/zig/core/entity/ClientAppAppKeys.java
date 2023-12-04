package com.zaggle.zig.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
public class ClientAppAppKeys {

    @EmbeddedId
    private ClientAppAppKeysId clientAppAppKeysId;
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnoreProperties({"clientApps", "clientUsers"})
    @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnoreProperties({"appApis", "clientApps", "appWebhooks", "appEvents"})
    @JoinColumn(name = "app_id", referencedColumnName = "id", insertable = false
            , updatable = false)
    private App app;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnoreProperties(value = "appAppKeys")
    @JoinColumn(name = "appkeys_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AppKeys appKeys;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    @Column(columnDefinition = "integer default 1")
    private int status;
}
