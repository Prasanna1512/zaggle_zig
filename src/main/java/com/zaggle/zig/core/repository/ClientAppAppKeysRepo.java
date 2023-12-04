package com.zaggle.zig.core.repository;

import com.zaggle.zig.core.entity.ClientAppAppKeys;
import com.zaggle.zig.core.entity.ClientAppAppKeysId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientAppAppKeysRepo extends JpaRepository<ClientAppAppKeys, ClientAppAppKeysId> {
    List<ClientAppAppKeys> findByClientName(String clientName);
    ClientAppAppKeys findByClientIdAndAppIdAndAppKeysId(UUID clientId, UUID appId, UUID appKeysId);

    @Transactional
    @Modifying
    @Query(value = "delete from client_app_app_keys where id=:clientAppAppKeysId", nativeQuery = true)
    void deleteByClientAppAppKeysId(@Param(value = "clientAppAppKeysId") UUID clientAppAppKeysId);
}
