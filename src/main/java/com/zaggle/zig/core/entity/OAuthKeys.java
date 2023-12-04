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
@Proxy(lazy = false)
//@TypeDef(name="jsonb", typeClass = JsonBinaryType.class)
public class OAuthKeys {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String clientId;
    @Column(unique = true)
    private String clientSecret;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode tokenData;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode refreshTokenData;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @Column(columnDefinition = "integer default 1")
    private int status;

    @ManyToOne
    @JoinColumn(name="app_id", referencedColumnName = "id", nullable=false)
    private App app;

    @ManyToOne
    @JoinColumn(name="corporate_id", referencedColumnName = "id", nullable=false)
    private Corporate corporate;
}
