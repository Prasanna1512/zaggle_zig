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
public class AppAppKeys {

    @EmbeddedId
    private AppAppKeysId appAppKeysId;
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnoreProperties(value = "appAppKeys")
    @JoinColumn(name = "appkeys_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AppKeys appKeys;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnoreProperties({"appApis", "clientApps", "appWebhooks", "appEvents"})
    @JoinColumn(name = "app_id", referencedColumnName = "id", insertable = false
            , updatable = false)
    private App app;
    @Column(columnDefinition = "integer default 1")
    private int status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}