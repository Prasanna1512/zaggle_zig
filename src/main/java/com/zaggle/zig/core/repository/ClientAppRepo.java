package com.zaggle.zig.core.repository;

import com.zaggle.zig.core.entity.ClientApp;
import com.zaggle.zig.core.entity.ClientAppId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientAppRepo extends JpaRepository<ClientApp, ClientAppId> {
    List<ClientApp> findByAppId(UUID clientId);
    List<ClientApp> findByClientId(UUID appId);
    ClientApp findByClientNameAndAppName(String clientName, String appName);

    @Transactional
    @Modifying
    @Query(value = "delete from client_app where id=:clientAppId", nativeQuery = true)
    void deleteByClientAppId(@Param(value = "clientAppId") UUID clientAppId);
}
