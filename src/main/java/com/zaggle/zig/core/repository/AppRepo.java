package com.zaggle.zig.core.repository;

import com.zaggle.zig.core.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppRepo extends JpaRepository<App, UUID> {
    App findByName(String name);
    List<App> findByNameContaining(String name);
}
