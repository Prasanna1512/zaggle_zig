package com.zaggle.zig.core.entity;


import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "api", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "domain", "uri"})})
@Proxy(lazy = false)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Api {

    //    @SequenceGenerator(name = "api_seq", sequenceName = "api_seq", allocationSize = 1)
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
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode headers;

//    @JsonIgnoreProperties("api")
//    @OneToOne( mappedBy = "api", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
//    private AppApi appApi;

}
