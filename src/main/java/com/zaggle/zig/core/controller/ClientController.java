package com.zaggle.zig.core.controller;

import com.zaggle.zig.core.domain.core.ClientReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.Client;
import com.zaggle.zig.core.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/core")
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/clients/get")
    public ResponseEntity<CoreRes<Client>> getAllClients() {
        CoreRes<Client> coreRes = clientService.getAllClients();
        if (coreRes.getElements().isEmpty()) {
            coreRes.setStatusCode(200);
            coreRes.setMessage("Client Data is empty");
            coreRes.setElements(new ArrayList<>());
            return new ResponseEntity<>(coreRes, HttpStatus.OK);
        }
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @GetMapping("/clients/get/{id}")
    public ResponseEntity<CoreRes<Client>> getClientById(@PathVariable("id") UUID id) {
        CoreRes<Client> coreRes = clientService.getClientById(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/clients/create")
    public ResponseEntity<CoreRes<Client>> createClient(@RequestBody ClientReq client) {
        CoreRes<Client> coreRes = clientService.createClient(client);
        return new ResponseEntity<>(coreRes, HttpStatus.CREATED);
    }

    @PutMapping("/clients/update")
    public ResponseEntity<CoreRes<Client>> updateClient(@RequestBody ClientReq client) {
        CoreRes<Client> coreRes = clientService.updateClient(client);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @DeleteMapping("/clients/delete/{id}")
    public ResponseEntity<CoreRes<Client>> deleteClient(@PathVariable("id") UUID id) {
        CoreRes<Client> coreRes = clientService.deleteClient(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @DeleteMapping("/clients/delete")
    public ResponseEntity<CoreRes<Client>> deleteAllClients() {
        CoreRes<Client> coreRes = clientService.deleteAllClients();
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PutMapping("/clients/update/secretkey/{id}")
    public ResponseEntity<CoreRes<Client>> updateSecretKey(@PathVariable("id") UUID id) {
        CoreRes<Client> coreRes = clientService.updateClientSecret(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PutMapping("/clients/update/associated/data/{id}")
    public ResponseEntity<CoreRes<Client>> updateAssociatedData(@PathVariable("id") UUID id) {
        CoreRes<Client> coreRes = clientService.updateAssociatedData(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PutMapping("/clients/update/signature/secret/{id}")
    public ResponseEntity<CoreRes<Client>> updateSignatureSecret(@PathVariable("id") UUID id) {
        CoreRes<Client> coreRes = clientService.updateSignatureSecret(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PutMapping("/clients/update/signature/secret")
    public ResponseEntity<CoreRes<Client>> updateSignature(@RequestParam String id, @RequestParam String secret) {
        CoreRes<Client> coreRes = clientService.updateSignature(id, secret);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }
}
