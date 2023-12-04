package com.zaggle.zig.core.repository;

import com.zaggle.zig.core.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepo extends JpaRepository<Client, UUID> {

    Client findByName(String name);
    List<Client> findByNameContaining(String name);


}
