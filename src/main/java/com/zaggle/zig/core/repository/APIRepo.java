package com.zaggle.zig.core.repository;

import com.zaggle.zig.core.entity.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface APIRepo extends JpaRepository<Api, UUID> {

    Api findByName(String name);
    List<Api> findByNameContaining(String name);
}
