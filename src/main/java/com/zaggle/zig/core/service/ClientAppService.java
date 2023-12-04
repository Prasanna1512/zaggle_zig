package com.zaggle.zig.core.service;

import com.zaggle.zig.core.domain.core.ClientAppReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.App;
import com.zaggle.zig.core.entity.Client;
import com.zaggle.zig.core.entity.ClientApp;
import com.zaggle.zig.core.entity.ClientAppId;
import com.zaggle.zig.core.repository.ClientAppRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ClientAppService {

    @Autowired
    ClientAppRepo clientAppRepo;

    @Autowired
    ClientService clientService;

    @Autowired
    AppService appService;

    public CoreRes<ClientApp> getAllClientApps() {
        CoreRes<ClientApp> coreRes = new CoreRes<>();
        try {
            coreRes.setStatusCode(200);
            coreRes.setElements(new ArrayList<>(clientAppRepo.findAll()));
            coreRes.setMessage("ClientApp mapping data fetched successfully");
        } catch (Exception e) {
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientApp> createClientApp(ClientAppReq clientAppReq) {
        CoreRes<ClientApp> coreRes = new CoreRes<>();
        List<ClientApp> clientApps = new ArrayList<>();
        HashMap<String, String> errorMap = new HashMap<>();
        try {
            coreRes.setStatusCode(200);
            if (clientAppReq == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Please input the required fields");
                return coreRes;
            }
            //changes made in this snippet
            UUID clientId = clientAppReq.getClientId();
            if (clientId == null) {
                coreRes.setMessage("Field clientId is required and should be a valid UUID");
                return coreRes;
            }
            if (clientAppReq.getAppIds() == null || clientAppReq.getAppIds().length == 0 ) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Please input the field i.e., array of appIds");
                return coreRes;
            }
            CoreRes<Client> clientCoreRes = clientService.getClientById(clientAppReq.getClientId());
            if(clientCoreRes.getElements() == null || clientCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("Client not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            //changes made in this snippet
            for (UUID appId: clientAppReq.getAppIds()) {
                ClientApp clientApp = new ClientApp();
                if (appId==null) {
                    errorMap.put("Error Message", "Field appId shouldn't be zero");
                    coreRes.setErrorMap(errorMap);
                    continue;
                }
                CoreRes<App> appCoreRes = appService.getAppsById(appId);
                if(appCoreRes.getElements() == null || appCoreRes.getElements().size() == 0) {
                    errorMap.put("Error Message of appId - " + appId, "App not found with given appId - " + appId);
                    coreRes.setErrorMap(errorMap);
                    continue;
                }
                clientApp.setClientAppId(new ClientAppId(clientAppReq.getClientId(), appId));
                clientApp.setId(UUID.randomUUID());
                clientApp.setClient(clientCoreRes.getElements().get(0));
                clientApp.setApp(appCoreRes.getElements().get(0));
                clientApp.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                clientApp.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                clientApp.setStatus(1);
                ClientApp _clientApp = clientAppRepo.save(clientApp);
                clientApps.add(_clientApp);
                coreRes.setElements(clientApps);
            }
            coreRes.setMessage("ClientApp mappings created successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientApp> getClientAppsByClientId(UUID id) {
        CoreRes<ClientApp> coreRes = new CoreRes<>();
        try{
            coreRes.setStatusCode(200);
            CoreRes<Client> clientCoreRes = clientService.getClientById(id);
            if(clientCoreRes.getElements() == null || clientCoreRes.getElements().size() == 0) {
                coreRes.setMessage("Client not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            List<ClientApp> clientApp = clientAppRepo.findByClientId(id);
            if (clientApp == null || clientApp.size() == 0) {
                coreRes.setMessage("ClientApp mapping data not found");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            coreRes.setElements(clientApp);
            coreRes.setMessage("ClientApp mapping data found");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientApp> getClientAppsByAppId(UUID id) {
        CoreRes<ClientApp> coreRes = new CoreRes<>();
        try{
            coreRes.setStatusCode(200);
            CoreRes<App> appCoreRes = appService.getAppsById(id);
            if(appCoreRes.getElements() == null || appCoreRes.getElements().size() == 0) {
                coreRes.setMessage("App not found with given appId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            List<ClientApp> clientApp = clientAppRepo.findByAppId(id);
            coreRes.setElements(clientApp);
            coreRes.setMessage("ClientApp mapping data found");
            if (clientApp == null || clientApp.size() == 0) {
                coreRes.setMessage("ClientApp mapping data not found");
            }
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientApp> getClientAppsByClientIdAndAppIds(ClientAppReq clientAppReq) {
        CoreRes<ClientApp> coreRes = new CoreRes<>();
        List<ClientApp> clientApps = new ArrayList<>();
        HashMap<String, String> errorMap = new HashMap<>();
        try{
            coreRes.setStatusCode(200);

            CoreRes<Client> clientCoreRes = clientService.getClientById(clientAppReq.getClientId());
            if(clientCoreRes.getElements() == null || clientCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("Client not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }

            if (clientAppReq.getAppIds().length == 0) {
                coreRes.setMessage("Please input the field i.e., array appIds");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }

            for(UUID appId: clientAppReq.getAppIds()) {
                CoreRes<App> appCoreRes = appService.getAppsById(appId);
                if(appCoreRes.getElements() == null || appCoreRes.getElements().size() == 0) {
                    errorMap.put("Error Message of appId - " + appId, "App not found with given appId - " + appId);
                    coreRes.setErrorMap(errorMap);
                    continue;
                }
                clientApps.add(clientAppRepo.findByClientNameAndAppName(clientCoreRes.getElements().get(0).getName(), appCoreRes.getElements().get(0).getName()));
            }
            coreRes.setElements(clientApps);
            coreRes.setMessage("ClientApp mappings data found");
        }
        catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientApp> deleteClientApp(ClientAppReq clientAppReq) {
        CoreRes<ClientApp> coreRes = new CoreRes<>();
        List<ClientApp> clientApps = new ArrayList<>();
        HashMap<String, String> errorMap = new HashMap<>();
        try {
            coreRes.setStatusCode(200);
            log.info("Client ID :: " + clientAppReq.getClientId());
            CoreRes<Client> clientCoreRes = clientService.getClientById(clientAppReq.getClientId());
            if(clientCoreRes.getElements() == null || clientCoreRes.getElements().size() == 0) {
                coreRes.setMessage("Client not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            if (clientAppReq.getAppIds().length == 0) {
                coreRes.setMessage("Please input the field i.e., array appIds");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            for (UUID appId : clientAppReq.getAppIds()) {
                CoreRes<App> appCoreRes = appService.getAppsById(appId);
                if(appCoreRes.getElements() == null || appCoreRes.getElements().size() == 0) {
                    errorMap.put("Error Message of appId - " + appId, "App not found with given appId - " + appId);
                    coreRes.setErrorMap(errorMap);
                    continue;
                }
                ClientApp clientApp = clientAppRepo.findByClientNameAndAppName(clientCoreRes.getElements().get(0).getName(), appCoreRes.getElements().get(0).getName());
                if (clientApp == null) {
                    errorMap.put("Error Message of appId - " + appId, "ClientApp mapping not found with given appId - " + appId);
                    coreRes.setErrorMap(errorMap);
                    continue;
                }
                clientApps.add(clientApp);
                clientAppRepo.deleteByClientAppId(clientApp.getId());
            }
            coreRes.setElements(clientApps);
            coreRes.setMessage("ClientApp Mappings Deleted Successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientApp> deleteClientAppByAppId(UUID id) {
        CoreRes<ClientApp> coreRes = new CoreRes<>();
        List<ClientApp> clientApps = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            log.info("App ID :: " + id);

            CoreRes<App> appCoreRes = appService.getAppsById(id);
            if(appCoreRes.getElements() == null || appCoreRes.getElements().size() == 0) {
                coreRes.setMessage("App not found with given appId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }

            List<ClientApp> clientAppList = clientAppRepo.findByAppId(id);
            if(clientAppList == null || clientAppList.size() == 0) {
                coreRes.setMessage("ClientApp Mappings not found with given appId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }


            for (ClientApp clientApp : clientAppList) {
                clientApps.add(clientApp);
                clientAppRepo.deleteByClientAppId(clientApp.getId());
            }
            coreRes.setElements(clientApps);
            coreRes.setMessage("ClientApp mappings deleted successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientApp> deleteClientAppByClientId(UUID id) {
        CoreRes<ClientApp> coreRes = new CoreRes<>();
        List<ClientApp> clientApps = new ArrayList<>();
        HashMap<String, String> errorMap = new HashMap<>();
        try {
            coreRes.setStatusCode(200);
            log.info("Client ID :: " + id);

            CoreRes<Client> clientCoreRes = clientService.getClientById(id);
            if(clientCoreRes.getElements() == null || clientCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("Client not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }

            List<ClientApp> clientAppList = clientAppRepo.findByClientId(id);
            if(clientAppList == null || clientAppList.size() == 0) {
                coreRes.setMessage("ClientApp Mappings not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }


            for (ClientApp clientApp : clientAppList) {
                clientApps.add(clientApp);
                clientAppRepo.deleteByClientAppId(clientApp.getId());
            }
            coreRes.setElements(clientApps);
            coreRes.setMessage("ClientApp Mappings Deleted Successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

}
