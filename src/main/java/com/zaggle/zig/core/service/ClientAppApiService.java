package com.zaggle.zig.core.service;

import com.zaggle.zig.core.domain.core.ClientAppApiReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.*;
import com.zaggle.zig.core.repository.ClientAppApiRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ClientAppApiService {

    @Autowired
    ClientAppApiRepo clientAppApiRepo;
    @Autowired
    ClientService clientService;
    @Autowired
    AppService appService;
    @Autowired
    APIService apiService;

    public CoreRes<ClientAppApi> getAllClientAppApis() {
        CoreRes<ClientAppApi> coreRes = new CoreRes<>();
        try {
            coreRes.setStatusCode(200);
            coreRes.setElements(new ArrayList<>(clientAppApiRepo.findAll()));
            coreRes.setMessage("AppApi mapping data fetched successfully");
        } catch (Exception e) {
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientAppApi> createClientAppApi(ClientAppApiReq clientAppApiReq) {
        CoreRes<ClientAppApi> coreRes = new CoreRes<>();
        List<ClientAppApi> clientAppApiArrayList = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            if (clientAppApiReq.getClientId() == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field clientId is required or clientId shouldn't be zero");
                return coreRes;
            }
            if (clientAppApiReq.getAppId() == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field appId is required or appId shouldn't be zero");
                return coreRes;
            }
            if (clientAppApiReq.getApiId().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field apiId is required");
                return coreRes;
            }
            CoreRes<Client> clientCoreRes = clientService.getClientById(clientAppApiReq.getClientId());
            if(clientCoreRes.getElements() == null || clientCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("Client not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            CoreRes<App> appCoreRes = appService.getAppsById(clientAppApiReq.getAppId());
            if(appCoreRes.getElements() == null || appCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("App not found with given appId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            CoreRes<Api> apiCoreRes = apiService.getAPIsById(UUID.fromString(clientAppApiReq.getApiId()));
            if(apiCoreRes.getElements() == null || apiCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("Api not found with given apiId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }

            ClientAppApi clientAppApi = new ClientAppApi();
            clientAppApi.setId(UUID.randomUUID());
            clientAppApi.setClientAppApiId(new ClientAppApiId(clientAppApiReq.getClientId(), clientAppApiReq.getAppId(), UUID.fromString(clientAppApiReq.getApiId())));
            clientAppApi.setClient(clientCoreRes.getElements().get(0));
            clientAppApi.setApp(appCoreRes.getElements().get(0));
            clientAppApi.setApi(apiCoreRes.getElements().get(0));
            clientAppApi.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            clientAppApi.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            clientAppApi.setStatus(1);
            ClientAppApi _clientAppApi = clientAppApiRepo.save(clientAppApi);

            clientAppApiArrayList.add(_clientAppApi);
            coreRes.setElements(clientAppApiArrayList);
            coreRes.setMessage("ClientAppApi mapping created successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientAppApi> getClientAppApisByClientId(UUID id) {
        CoreRes<ClientAppApi> coreRes = new CoreRes<>();
        try{
            coreRes.setStatusCode(200);
            CoreRes<Client> clientCoreRes = clientService.getClientById(id);
            if(clientCoreRes.getElements() == null || clientCoreRes.getElements().size() == 0) {
                coreRes.setMessage("Client not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            List<ClientAppApi> clientAppApi = clientAppApiRepo.findByClientId(id);
            if (clientAppApi == null || clientAppApi.size() == 0) {
                coreRes.setMessage("ClientAppApi mapping data not found");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            coreRes.setElements(clientAppApi);
            coreRes.setMessage("ClientAppApi mapping data found");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientAppApi> getClientAppApisByAppId(UUID id) {
        CoreRes<ClientAppApi> coreRes = new CoreRes<>();
        try{
            coreRes.setStatusCode(200);
            CoreRes<App> appCoreRes = appService.getAppsById(id);
            if(appCoreRes.getElements() == null || appCoreRes.getElements().size() == 0) {
                coreRes.setMessage("App not found with given appId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            List<ClientAppApi> clientAppApi = clientAppApiRepo.findByAppId(id);
            coreRes.setElements(clientAppApi);
            coreRes.setMessage("ClientAppApi mapping data found");
            if (clientAppApi == null || clientAppApi.size() == 0) {
                coreRes.setMessage("ClientAppApi mapping data not found");
            }
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientAppApi> getClientAppApisByAPIId(UUID id) {
        CoreRes<ClientAppApi> coreRes = new CoreRes<>();
        try{
            coreRes.setStatusCode(200);
            CoreRes<Api> apiCoreRes = apiService.getAPIsById(id);
            if(apiCoreRes.getElements() == null || apiCoreRes.getElements().size() == 0) {
                coreRes.setMessage("API not found with given apiId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            List<ClientAppApi> clientAppApi = clientAppApiRepo.findByApiId(id);
            coreRes.setElements(clientAppApi);
            coreRes.setMessage("ClientAppApi mapping data found");
            if (clientAppApi == null || clientAppApi.size() == 0) {
                coreRes.setMessage("ClientAppApi mapping data not found");
            }
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientAppApi> deleteClientAppApi(ClientAppApiReq clientAppApiReq) {
        CoreRes<ClientAppApi> coreRes = new CoreRes<>();
        List<ClientAppApi> clientAppApiList = new ArrayList<>();
        try{
            coreRes.setStatusCode(200);
            coreRes.setStatusCode(200);
            if (clientAppApiReq.getClientId() == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field clientId is required or clientId shouldn't be zero");
                return coreRes;
            }
            if (clientAppApiReq.getAppId() == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field appId is required or appId shouldn't be zero");
                return coreRes;
            }
            if (clientAppApiReq.getApiId().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field apiId is required");
                return coreRes;
            }
            CoreRes<Client> clientCoreRes = clientService.getClientById(clientAppApiReq.getClientId());
            if(clientCoreRes.getElements() == null || clientCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("Client not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            CoreRes<App> appCoreRes = appService.getAppsById(clientAppApiReq.getAppId());
            if(appCoreRes.getElements() == null || appCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("App not found with given appId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            CoreRes<Api> apiCoreRes = apiService.getAPIsById(UUID.fromString(clientAppApiReq.getApiId()));
            if(apiCoreRes.getElements() == null || apiCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("Api not found with given apiId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }

            ClientAppApi clientAppApi = clientAppApiRepo.findByClientNameAndAppNameAndApiName(clientCoreRes.getElements().get(0).getName(), appCoreRes.getElements().get(0).getName(), apiCoreRes.getElements().get(0).getName());
            clientAppApiList.add(clientAppApi);
            coreRes.setElements(clientAppApiList);
            clientAppApiRepo.deleteByClientAppApiId(clientAppApi.getId());
            coreRes.setMessage("ClientAppApi mapping deleted successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }
}
