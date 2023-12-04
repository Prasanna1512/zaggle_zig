package com.zaggle.zig.core.controller;

import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.domain.core.WebhookReq;
import com.zaggle.zig.core.entity.Webhook;
import com.zaggle.zig.core.service.WebhookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.UUID;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/core")
public class WebhookController {

    @Autowired
    WebhookService webhookService;

    @GetMapping("/webhooks/get")
    public ResponseEntity<CoreRes<Webhook>> getAllWebhooks() {
        CoreRes<Webhook> coreRes = webhookService.getAllWebhooks();
        if (coreRes.getElements().isEmpty()) {
            coreRes.setStatusCode(200);
            coreRes.setMessage("Webhook Data is empty");
            coreRes.setElements(new ArrayList<>());
            return new ResponseEntity<>(coreRes, HttpStatus.OK);
        }
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @GetMapping("/webhooks/get/{id}")
    public ResponseEntity<CoreRes<Webhook>> getWebhookById(@PathVariable("id") UUID id) {
        CoreRes<Webhook> coreRes = webhookService.getWebhooksById(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/webhooks/create")
    public ResponseEntity<CoreRes<Webhook>> createWebhook(@RequestBody WebhookReq webhook) {
        CoreRes<Webhook> coreRes = webhookService.createWebhook(webhook);
        return new ResponseEntity<>(coreRes, HttpStatus.CREATED);
    }

    @PutMapping("/webhooks/update")
    public ResponseEntity<CoreRes<Webhook>> updateWebhook(@RequestBody WebhookReq webhook) {
        CoreRes<Webhook> coreRes = webhookService.updateWebhook(webhook);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @DeleteMapping("/webhooks/delete/{id}")
    public ResponseEntity<CoreRes<Webhook>> deleteWebhook(@PathVariable("id") UUID id) {
        CoreRes<Webhook> coreRes = webhookService.deleteWebhook(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @DeleteMapping("/webhooks/delete")
    public ResponseEntity<CoreRes<Webhook>> deleteAllWebhooks() {
        CoreRes<Webhook> coreRes = webhookService.deleteAllWebhooks();
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

}
