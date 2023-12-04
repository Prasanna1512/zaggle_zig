package com.zaggle.zig.core.repository;

import com.zaggle.zig.core.entity.ClientAppApi;
import com.zaggle.zig.core.entity.ClientAppApiId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientAppApiRepo extends JpaRepository<ClientAppApi, ClientAppApiId> {
    List<ClientAppApi> findByAppId(UUID appId);
    List<ClientAppApi> findByClientId(UUID clientId);
    List<ClientAppApi> findByApiId(UUID apiId);
    List<ClientAppApi> findByClientIdAndAppId(UUID clientId, UUID appId);
    ClientAppApi findByClientNameAndAppNameAndApiName(String clientName, String appName, String apiName);

    @Transactional
    @Modifying
    @Query(value = "delete from  client_app_api where id=:clientAppApiId", nativeQuery = true)
    void deleteByClientAppApiId(@Param(value = "clientAppApiId") UUID clientAppApiId);
}
