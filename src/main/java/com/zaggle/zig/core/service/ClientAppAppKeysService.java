package com.zaggle.zig.core.service;

import com.zaggle.zig.core.domain.core.ClientAppAppKeysReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.*;
import com.zaggle.zig.core.repository.ClientAppAppKeysRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ClientAppAppKeysService {

    @Autowired
    ClientAppAppKeysRepo clientAppAppKeysRepo;
    @Autowired
    ClientService clientService;
    @Autowired
    AppService appService;
    @Autowired
    AppKeysService appKeysService;

    public CoreRes<ClientAppAppKeys> getAllClientAppAppKeys() {
        CoreRes<ClientAppAppKeys> coreRes = new CoreRes<>();
        try {
            coreRes.setStatusCode(200);
            coreRes.setElements(new ArrayList<>(clientAppAppKeysRepo.findAll()));
            coreRes.setMessage("ClientAppAppKeys mapping data fetched successfully");
        } catch (Exception e) {
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientAppAppKeys> createClientAppAppKeys(ClientAppAppKeysReq clientAppAppKeysReq) {
        CoreRes<ClientAppAppKeys> coreRes = new CoreRes<>();
        List<ClientAppAppKeys> clientAppAppKeysList = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            if (clientAppAppKeysReq == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Please input the required fields");
                return coreRes;
            }
            if (clientAppAppKeysReq.getClientId() == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field clientId is required or clientId shouldn't be zero");
                return coreRes;
            }

            if (clientAppAppKeysReq.getAppId() == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field appId is required or appId shouldn't be zero");
                return coreRes;
            }
            if (clientAppAppKeysReq.getAppKeysId().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field appKeysId is required");
                return coreRes;
            }
            CoreRes<Client> clientCoreRes = clientService.getClientById(clientAppAppKeysReq.getClientId());
            if(clientCoreRes.getElements() == null || clientCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("Client not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            CoreRes<App> appCoreRes = appService.getAppsById(clientAppAppKeysReq.getAppId());
            if(appCoreRes.getElements() == null || appCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("App not found with given appId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            CoreRes<AppKeys> appKeysCoreRes = appKeysService.getAppKeysById(UUID.fromString(clientAppAppKeysReq.getAppKeysId()));
            if(appKeysCoreRes.getElements() == null || appKeysCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("AppKeys not found with given appId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }

            ClientAppAppKeys clientAppAppKeys = new ClientAppAppKeys();
            clientAppAppKeys.setClientAppAppKeysId(new ClientAppAppKeysId(clientAppAppKeysReq.getClientId(), clientAppAppKeysReq.getAppId(), UUID.fromString(clientAppAppKeysReq.getAppKeysId())));
            clientAppAppKeys.setId(UUID.randomUUID());
            clientAppAppKeys.setAppKeys(appKeysCoreRes.getElements().get(0));
            clientAppAppKeys.setApp(appCoreRes.getElements().get(0));
            clientAppAppKeys.setClient(clientCoreRes.getElements().get(0));
            clientAppAppKeys.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            clientAppAppKeys.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            clientAppAppKeys.setStatus(1);
            ClientAppAppKeys _clientAppAppKeys = clientAppAppKeysRepo.save(clientAppAppKeys);
            clientAppAppKeysList.add(_clientAppAppKeys);
            coreRes.setElements(clientAppAppKeysList);
            coreRes.setMessage("ClientAppAppKeys mappings created successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientAppAppKeys> deleteClientAppAppKeys(ClientAppAppKeysReq clientAppAppKeysReq) {
        CoreRes<ClientAppAppKeys> coreRes = new CoreRes<>();
        List<ClientAppAppKeys> clientAppAppKeysList = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            ClientAppAppKeys clientAppAppKeys = clientAppAppKeysRepo.findByClientIdAndAppIdAndAppKeysId(clientAppAppKeysReq.getClientId(),clientAppAppKeysReq.getAppId(),UUID.fromString(clientAppAppKeysReq.getAppKeysId()));
            clientAppAppKeysList.add(clientAppAppKeys);
            coreRes.setElements(clientAppAppKeysList);
            clientAppAppKeysRepo.deleteByClientAppAppKeysId(clientAppAppKeys.getId());
            coreRes.setMessage("ClientAppAppKeys mapping deleted successfully");
        } catch (Exception e) {
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }
}
