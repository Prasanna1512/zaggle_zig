package com.zaggle.zig.core.repository;

import com.zaggle.zig.core.entity.ClientAppWebhook;
import com.zaggle.zig.core.entity.ClientAppWebhookId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClientAppWebhookRepo extends JpaRepository<ClientAppWebhook, ClientAppWebhookId> {
    List<ClientAppWebhook> findByAppId(UUID appId);
    List<ClientAppWebhook> findByClientId(UUID clientId);
    List<ClientAppWebhook> findByWebhookId(UUID webhookId);
    List<ClientAppWebhook> findByClientIdAndAppId(UUID clientId, UUID appId);
    ClientAppWebhook findByClientNameAndAppNameAndWebhookName(String clientName, String appName, String webhookName);

    @Transactional
    @Modifying
    @Query(value = "delete from  client_app_webhook where id=:clientAppWebhookId", nativeQuery = true)
    void deleteByClientAppWebhookId(@Param(value = "clientAppWebhookId") UUID clientAppWebhookId);
}
