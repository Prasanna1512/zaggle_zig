package com.zaggle.zig.core.repository;

import com.zaggle.zig.core.entity.AppKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppKeysRepo extends JpaRepository<AppKeys, UUID> {
    AppKeys findByKeyName(String name);
}
