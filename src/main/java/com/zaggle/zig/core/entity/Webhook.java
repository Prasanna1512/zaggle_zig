package com.zaggle.zig.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="webhook", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "domain", "uri" }) })
@Proxy(lazy = false)
//@TypeDef(name="jsonb", typeClass = JsonBinaryType.class)
public class Webhook {

    @Id
    private UUID id;
    private String name;
    private String description;
    @Column(columnDefinition = "integer default 1")
    private int status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String domain;
    private String uri;
    private String method;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode headers;
    @JsonIgnoreProperties("webhook")
    @OneToOne( mappedBy = "webhook", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private AppWebhook appWebhook;

}
