package com.zaggle.zig.core.controller;

import com.zaggle.zig.core.domain.core.ClientAppReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.ClientApp;
import com.zaggle.zig.core.service.ClientAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/core")
@Slf4j
public class ClientAppController {

    @Autowired
    ClientAppService clientAppService;

    @GetMapping("/clientapps/get")
    public ResponseEntity<CoreRes<ClientApp>> getAllClientApps() {
        CoreRes<ClientApp> coreRes = clientAppService.getAllClientApps();
        if (coreRes.getElements().isEmpty()) {
            coreRes.setStatusCode(200);
            coreRes.setMessage("ClientApp Data is empty");
            coreRes.setElements(new ArrayList<>());
            return new ResponseEntity<>(coreRes, HttpStatus.OK);
        }
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/clientapps/create")
    public ResponseEntity<CoreRes<ClientApp>> createClientApp(@RequestBody ClientAppReq appApiReq) {
        CoreRes<ClientApp> coreRes = clientAppService.createClientApp(appApiReq);
        return new ResponseEntity<>(coreRes, HttpStatus.CREATED);
    }

    @GetMapping("/clientapps/get/app/{id}")
    public ResponseEntity<CoreRes<ClientApp>> getClientAppsByAppId(@PathVariable(value = "id") UUID id) {
        CoreRes<ClientApp> coreRes = clientAppService.getClientAppsByAppId(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @GetMapping("/clientapps/get/client/{id}")
    public ResponseEntity<CoreRes<ClientApp>> getClientAppsByClientId(@PathVariable(value = "id") UUID id) {
        CoreRes<ClientApp> coreRes = clientAppService.getClientAppsByClientId(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/clientapps/get")
    public ResponseEntity<CoreRes<ClientApp>> getClientAppsByAppAndAPIId(@RequestBody ClientAppReq appApiReq) {
        CoreRes<ClientApp> coreRes = clientAppService.getClientAppsByClientIdAndAppIds(appApiReq);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/clientapps/delete")
    public ResponseEntity<CoreRes<ClientApp>> deleteClientAppByAppAndApiIds(@RequestBody ClientAppReq appApiReq) {
        CoreRes<ClientApp> coreRes = clientAppService.deleteClientApp(appApiReq);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @DeleteMapping("/clientapps/delete/app/{id}")
    public ResponseEntity<CoreRes<ClientApp>> deleteClientAppsByAppId(@PathVariable(value = "id") UUID id) {
        CoreRes<ClientApp> coreRes = clientAppService.deleteClientAppByAppId(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @DeleteMapping("/clientapps/delete/client/{id}")
    public ResponseEntity<CoreRes<ClientApp>> deleteClientAppsByApiId(@PathVariable(value = "id") UUID id) {
        CoreRes<ClientApp> coreRes = clientAppService.deleteClientAppByClientId(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }
}
