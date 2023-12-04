package com.zaggle.zig.core.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@TypeDef(name="jsonb", typeClass = JsonBinaryType.class)
@Proxy(lazy = false)
public class OAuthMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode callBackParams;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode tokenParams;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode refreshTokenParams;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    @Column(columnDefinition = "integer default 1")
    private int status;

    @OneToOne
    @JoinColumn(name="app_id", referencedColumnName = "id", nullable=false)
    private App app;

}
