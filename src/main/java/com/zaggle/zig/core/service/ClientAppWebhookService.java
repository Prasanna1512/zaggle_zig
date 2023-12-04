package com.zaggle.zig.core.service;

import com.zaggle.zig.core.domain.core.ClientAppWebhookReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.*;
import com.zaggle.zig.core.repository.ClientAppWebhookRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ClientAppWebhookService {

    @Autowired
    ClientAppWebhookRepo clientAppWebhookRepo;
    @Autowired
    ClientService clientService;
    @Autowired
    AppService appService;
    @Autowired
    WebhookService webhookService;

    public CoreRes<ClientAppWebhook> getAllClientAppWebhooks() {
        CoreRes<ClientAppWebhook> coreRes = new CoreRes<>();
        try {
            coreRes.setStatusCode(200);
            coreRes.setElements(new ArrayList<>(clientAppWebhookRepo.findAll()));
            coreRes.setMessage("AppApi mapping data fetched successfully");
        } catch (Exception e) {
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientAppWebhook> createClientAppWebhook(ClientAppWebhookReq clientAppWebhookReq) {
        CoreRes<ClientAppWebhook> coreRes = new CoreRes<>();
        List<ClientAppWebhook> clientAppWebhookArrayList = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            if (clientAppWebhookReq.getClientId() == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field clientId is required or clientId shouldn't be zero");
                return coreRes;
            }
            if (clientAppWebhookReq.getAppId() == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field appId is required or appId shouldn't be zero");
                return coreRes;
            }
            if (clientAppWebhookReq.getWebhookId().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field webhookId is required");
                return coreRes;
            }
            CoreRes<Client> clientCoreRes = clientService.getClientById(clientAppWebhookReq.getClientId());
            if(clientCoreRes.getElements() == null || clientCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("Client not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            CoreRes<App> appCoreRes = appService.getAppsById(clientAppWebhookReq.getAppId());
            if(appCoreRes.getElements() == null || appCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("App not found with given appId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            CoreRes<Webhook> webhookCoreRes = webhookService.getWebhooksById(UUID.fromString(clientAppWebhookReq.getWebhookId()));
            if(webhookCoreRes.getElements() == null || webhookCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("Webhook not found with given webhookId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }

            ClientAppWebhook clientAppWebhook = new ClientAppWebhook();
            clientAppWebhook.setId(UUID.randomUUID());
            clientAppWebhook.setClientAppWebhookId(new ClientAppWebhookId(clientAppWebhookReq.getClientId(), clientAppWebhookReq.getAppId(), UUID.fromString(clientAppWebhookReq.getWebhookId())));
            clientAppWebhook.setClient(clientCoreRes.getElements().get(0));
            clientAppWebhook.setApp(appCoreRes.getElements().get(0));
            clientAppWebhook.setWebhook(webhookCoreRes.getElements().get(0));
            clientAppWebhook.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            clientAppWebhook.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            clientAppWebhook.setStatus(1);
            ClientAppWebhook _clientAppWebhook = clientAppWebhookRepo.save(clientAppWebhook);

            clientAppWebhookArrayList.add(_clientAppWebhook);
            coreRes.setElements(clientAppWebhookArrayList);
            coreRes.setMessage("ClientAppWebhook mapping created successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientAppWebhook> getClientAppWebhooksByClientId(UUID id) {
        CoreRes<ClientAppWebhook> coreRes = new CoreRes<>();
        try{
            coreRes.setStatusCode(200);
            CoreRes<Client> clientCoreRes = clientService.getClientById(id);
            if(clientCoreRes.getElements() == null || clientCoreRes.getElements().size() == 0) {
                coreRes.setMessage("Client not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            List<ClientAppWebhook> clientAppWebhook = clientAppWebhookRepo.findByClientId(id);
            if (clientAppWebhook == null || clientAppWebhook.size() == 0) {
                coreRes.setMessage("ClientAppWebhook mapping data not found");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            coreRes.setElements(clientAppWebhook);
            coreRes.setMessage("ClientAppWebhook mapping data found");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientAppWebhook> getClientAppWebhooksByAppId(UUID id) {
        CoreRes<ClientAppWebhook> coreRes = new CoreRes<>();
        try{
            coreRes.setStatusCode(200);
            CoreRes<App> appCoreRes = appService.getAppsById(id);
            if(appCoreRes.getElements() == null || appCoreRes.getElements().size() == 0) {
                coreRes.setMessage("App not found with given appId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            List<ClientAppWebhook> clientAppWebhook = clientAppWebhookRepo.findByAppId(id);
            coreRes.setElements(clientAppWebhook);
            coreRes.setMessage("ClientAppWebhook mapping data found");
            if (clientAppWebhook == null || clientAppWebhook.size() == 0) {
                coreRes.setMessage("ClientAppWebhook mapping data not found");
            }
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientAppWebhook> getClientAppWebhooksByWebhookId(UUID id) {
        CoreRes<ClientAppWebhook> coreRes = new CoreRes<>();
        try{
            coreRes.setStatusCode(200);
            CoreRes<Webhook> webhookCoreRes = webhookService.getWebhooksById(id);
            if(webhookCoreRes.getElements() == null || webhookCoreRes.getElements().size() == 0) {
                coreRes.setMessage("API not found with given apiId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            List<ClientAppWebhook> clientAppWebhook = clientAppWebhookRepo.findByWebhookId(id);
            coreRes.setElements(clientAppWebhook);
            coreRes.setMessage("ClientAppWebhook mapping data found");
            if (clientAppWebhook == null || clientAppWebhook.size() == 0) {
                coreRes.setMessage("ClientAppWebhook mapping data not found");
            }
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<ClientAppWebhook> deleteClientAppWebhook(ClientAppWebhookReq clientAppWebhookReq) {
        CoreRes<ClientAppWebhook> coreRes = new CoreRes<>();
        List<ClientAppWebhook> clientAppWebhookList = new ArrayList<>();
        try{
            coreRes.setStatusCode(200);
            coreRes.setStatusCode(200);
            if (clientAppWebhookReq.getClientId() == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field clientId is required or clientId shouldn't be zero");
                return coreRes;
            }
            if (clientAppWebhookReq.getAppId() == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field appId is required or appId shouldn't be zero");
                return coreRes;
            }
            if (clientAppWebhookReq.getWebhookId().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field webhookId is required");
                return coreRes;
            }
            CoreRes<Client> clientCoreRes = clientService.getClientById(clientAppWebhookReq.getClientId());
            if(clientCoreRes.getElements() == null || clientCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("Client not found with given clientId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            CoreRes<App> appCoreRes = appService.getAppsById(clientAppWebhookReq.getAppId());
            if(appCoreRes.getElements() == null || appCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("App not found with given appId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }
            CoreRes<Webhook> webhookCoreRes = webhookService.getWebhooksById(UUID.fromString(clientAppWebhookReq.getWebhookId()));
            if(webhookCoreRes.getElements() == null || webhookCoreRes.getElements().size() == 0 ) {
                coreRes.setMessage("Webhook not found with given webhookId");
                coreRes.setElements(new ArrayList<>());
                return coreRes;
            }

            ClientAppWebhook clientAppWebhook = clientAppWebhookRepo.findByClientNameAndAppNameAndWebhookName(clientCoreRes.getElements().get(0).getName(), appCoreRes.getElements().get(0).getName(), webhookCoreRes.getElements().get(0).getName());
            clientAppWebhookList.add(clientAppWebhook);
            coreRes.setElements(clientAppWebhookList);
            clientAppWebhookRepo.deleteByClientAppWebhookId(clientAppWebhook.getId());
            coreRes.setMessage("ClientAppWebhook mapping deleted successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }
}
