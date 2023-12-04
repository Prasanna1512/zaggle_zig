package com.zaggle.zig.core.controller;

import com.zaggle.zig.core.domain.core.ClientAppWebhookReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.ClientAppWebhook;
import com.zaggle.zig.core.service.ClientAppWebhookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/core")
@Slf4j
@CrossOrigin
public class ClientAppWebhookController {

    @Autowired
    ClientAppWebhookService clientAppWebhookService;

    @GetMapping("/clientappwebhooks/get")
    public ResponseEntity<CoreRes<ClientAppWebhook>> getAllClientAppWebhooks() {
        CoreRes<ClientAppWebhook> coreRes = clientAppWebhookService.getAllClientAppWebhooks();
        if (coreRes.getElements().isEmpty()) {
            coreRes.setStatusCode(200);
            coreRes.setMessage("ClientAppWebhook Data is empty");
            coreRes.setElements(new ArrayList<>());
            return new ResponseEntity<>(coreRes, HttpStatus.OK);
        }
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/clientappwebhooks/create")
    public ResponseEntity<CoreRes<ClientAppWebhook>> createClientAppWebhook(@RequestBody ClientAppWebhookReq app) {
        CoreRes<ClientAppWebhook> coreRes = clientAppWebhookService.createClientAppWebhook(app);
        return new ResponseEntity<>(coreRes, HttpStatus.CREATED);
    }

    @GetMapping("/clientappwebhooks/get/app/{id}")
    public ResponseEntity<CoreRes<ClientAppWebhook>> getClientAppWebhookByAppId(@PathVariable(value = "id") UUID id) {
        CoreRes<ClientAppWebhook> coreRes = clientAppWebhookService.getClientAppWebhooksByAppId(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @GetMapping("/clientappwebhooks/get/client/{id}")
    public ResponseEntity<CoreRes<ClientAppWebhook>> getClientAppWebhooksByClientId(@PathVariable(value = "id") UUID id) {
        CoreRes<ClientAppWebhook> coreRes = clientAppWebhookService.getClientAppWebhooksByClientId(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @GetMapping("/clientappwebhooks/get/webhook/{id}")
    public ResponseEntity<CoreRes<ClientAppWebhook>> getClientAppWebhooksByWebhookId(@PathVariable(value = "id") UUID id) {
        CoreRes<ClientAppWebhook> coreRes = clientAppWebhookService.getClientAppWebhooksByWebhookId(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/clientappwebhooks/delete")
    public ResponseEntity<CoreRes<ClientAppWebhook>> deleteClientAppWebhook(@RequestBody ClientAppWebhookReq clientAppWebhookReq) {
        CoreRes<ClientAppWebhook> coreRes = clientAppWebhookService.deleteClientAppWebhook(clientAppWebhookReq);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }
}
//hellooo