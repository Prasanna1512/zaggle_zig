package com.zaggle.zig.core.controller;

import com.zaggle.zig.core.domain.core.ClientAppAppKeysReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.ClientAppAppKeys;
import com.zaggle.zig.core.service.ClientAppAppKeysService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/core")
@Slf4j
public class ClientAppAppKeysController {
    @Autowired
    ClientAppAppKeysService clientAppAppKeysService;

    @GetMapping("/clientappappkeys/get")
    public ResponseEntity<CoreRes<ClientAppAppKeys>> getAllClientAppAppKeys() {
        CoreRes<ClientAppAppKeys> coreRes = clientAppAppKeysService.getAllClientAppAppKeys();
        if (coreRes.getElements().isEmpty()) {
            coreRes.setStatusCode(200);
            coreRes.setMessage("ClientAppAppKeys Data is empty");
            coreRes.setElements(new ArrayList<>());
            return new ResponseEntity<>(coreRes, HttpStatus.OK);
        }
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/clientappappkeys/create")
    public ResponseEntity<CoreRes<ClientAppAppKeys>> createClientAppApKeys(@RequestBody ClientAppAppKeysReq clientAppAppKeysReq) {
        CoreRes<ClientAppAppKeys> coreRes = clientAppAppKeysService.createClientAppAppKeys(clientAppAppKeysReq);
        return new ResponseEntity<>(coreRes, HttpStatus.CREATED);
    }

    @PostMapping("/clientappappkeys/delete")
    public ResponseEntity<CoreRes<ClientAppAppKeys>> deleteAppApKeysByAppAndApiIds(@RequestBody ClientAppAppKeysReq clientAppAppKeysReq) {
        CoreRes<ClientAppAppKeys> coreRes = clientAppAppKeysService.deleteClientAppAppKeys(clientAppAppKeysReq);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

}
