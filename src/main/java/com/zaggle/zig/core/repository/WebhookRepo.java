package com.zaggle.zig.core.repository;

import com.zaggle.zig.core.entity.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WebhookRepo extends JpaRepository<Webhook, UUID> {

    Webhook findByName(String name);
    List<Webhook> findByNameContaining(String name);
}
