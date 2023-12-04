package com.zaggle.zig.core.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.domain.core.WebhookReq;
import com.zaggle.zig.core.entity.Webhook;
import com.zaggle.zig.core.repository.WebhookRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class WebhookService {
    @Autowired
    WebhookRepo webhookRepo;
    ObjectMapper mapper = new ObjectMapper();

    public CoreRes<Webhook> getAllWebhooks() {
        CoreRes<Webhook> coreRes = new CoreRes<>();
        try {
            coreRes.setStatusCode(200);
            coreRes.setElements(new ArrayList<>(webhookRepo.findAll()));
            coreRes.setMessage("Webhook Data Fetched Successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Webhook> getWebhooksById(UUID webhookId) {
        CoreRes<Webhook> coreRes = new CoreRes<>();
        try {
            Optional<Webhook> webhook = webhookRepo.findById(webhookId);
            coreRes.setStatusCode(200);
            if (webhook.isPresent()) {
                List<Webhook> webhooks = new ArrayList<>();
                webhooks.add(webhook.get());
                coreRes.setElements(webhooks);
                coreRes.setMessage("Webhook found");
            } else {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("No Webhook Id matched with given Id");
            }
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Webhook> createWebhook(WebhookReq webhookReq) {
        CoreRes<Webhook> coreRes = new CoreRes<>();
        List<Webhook> clients = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            if (webhookReq == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Please input the required fields");
                return coreRes;
            }
            if (webhookReq.getName() == null || webhookReq.getName().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field Name is required");
                return coreRes;
            }
            if (webhookReq.getDomain() == null || webhookReq.getDomain().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field Domain is required");
                return coreRes;
            }
            if (webhookReq.getUri() == null || webhookReq.getUri().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field URI is required");
                return coreRes;
            }
            if (webhookReq.getMethod() == null || webhookReq.getMethod().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field Method is required");
                return coreRes;
            }
            if (webhookReq.getHeaders() == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field Headers is required");
                return coreRes;
            }
            Webhook webhook = new Webhook();
            webhook.setId(UUID.randomUUID());
            webhook.setName(webhookReq.getName());
            webhook.setDescription(webhookReq.getDescription() != null ? webhook.getDescription() : "");
            webhook.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            webhook.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            webhook.setStatus(1);
            webhook.setDomain(webhookReq.getDomain());
            webhook.setUri(webhookReq.getUri());
            webhook.setMethod(webhookReq.getMethod());
            ObjectMapper objectMapper = new ObjectMapper();
            String headersJson = objectMapper.writeValueAsString(webhookReq.getHeaders());
            JsonNode node = objectMapper.readTree(headersJson);
            webhook.setHeaders(node);
            Webhook _webhook = webhookRepo.save(webhook);
            clients.add(_webhook);
            coreRes.setElements(clients);
            coreRes.setMessage("Webhook created successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
            if (e.getMessage().equalsIgnoreCase("A JSONObject text must begin with '{' at 1 [character 2 line 1]")) {
                coreRes.setMessage("Headers must be a json object");
            }
        }
        return coreRes;
    }

    public CoreRes<Webhook> updateWebhook(WebhookReq webhookReq) {
        CoreRes<Webhook> coreRes = new CoreRes<>();
        List<Webhook> webhooks = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            Webhook _webhook = webhookRepo.findById(webhookReq.getId())
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("No Webhook found with given ID = ", webhookReq.getId(), null));
            if(_webhook.getStatus() == -1 && webhookReq.getStatus() != 1) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Webhook is in inactive state. Please activate the Webhook to update");
                return coreRes;
            }

            _webhook.setName((webhookReq.getName() != null && !webhookReq.getName().equalsIgnoreCase("")) ? webhookReq.getName() : _webhook.getName());
            _webhook.setDescription((webhookReq.getDescription() != null && !webhookReq.getDescription().equalsIgnoreCase("")) ? webhookReq.getDescription() : _webhook.getDescription());
            _webhook.setStatus((webhookReq.getStatus() == -1 || webhookReq.getStatus() == 1) ? webhookReq.getStatus() : _webhook.getStatus());
            _webhook.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            _webhook.setDomain((webhookReq.getDomain() != null && !webhookReq.getDomain().equalsIgnoreCase("")) ? webhookReq.getDomain() : _webhook.getDomain());
            _webhook.setUri((webhookReq.getUri() != null && !webhookReq.getUri().equalsIgnoreCase("")) ? webhookReq.getUri() : _webhook.getUri());
            _webhook.setMethod((webhookReq.getMethod() != null && !webhookReq.getMethod().equalsIgnoreCase("")) ? webhookReq.getMethod() : _webhook.getMethod());
            ObjectMapper objectMapper = new ObjectMapper();
            String headersJson = objectMapper.writeValueAsString(webhookReq.getHeaders() != null ? webhookReq.getHeaders() : _webhook.getHeaders());
            JsonNode node = objectMapper.readTree(headersJson);
            _webhook.setHeaders(node);
            Webhook updatedWebhook = webhookRepo.save(_webhook);

            webhooks.add(updatedWebhook);
            coreRes.setElements(webhooks);
            coreRes.setMessage("Webhook updated Successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
            if (e.getMessage().equalsIgnoreCase("A JSONObject text must begin with '{' at 1 [character 2 line 1]")) {
                coreRes.setMessage("Headers must be a json object");
            }
        }
        return coreRes;
    }

    public CoreRes<Webhook> deleteWebhook(UUID id) {
        CoreRes<Webhook> coreRes = new CoreRes<>();
        List<Webhook> webhooks = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            Webhook _webhook = webhookRepo.findById(id)
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("No Webhook found with given ID = ", id, null));
            webhookRepo.deleteById(id);
            webhooks.add(_webhook);
            coreRes.setElements(webhooks);
            coreRes.setMessage("Webhook with given Id deleted successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Webhook> deleteAllWebhooks() {
        CoreRes<Webhook> coreRes = new CoreRes<>();
        coreRes.setStatusCode(200);
        webhookRepo.deleteAll();
        coreRes.setElements(new ArrayList<>());
        coreRes.setMessage("Webhooks deleted successfully");
        return coreRes;
    }
}
