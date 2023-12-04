package com.zaggle.zig.core.controller;
import com.zaggle.zig.core.domain.core.ClientAppApiReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.ClientAppApi;
import com.zaggle.zig.core.service.ClientAppApiService;
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
public class ClientAppApiController {

    @Autowired
    ClientAppApiService clientAppApiService;

    @GetMapping("/clientappapis/get")
    public ResponseEntity<CoreRes<ClientAppApi>> getAllClientAppApis() {
        CoreRes<ClientAppApi> coreRes = clientAppApiService.getAllClientAppApis();
        if (coreRes.getElements().isEmpty()) {
            coreRes.setStatusCode(200);
            coreRes.setMessage("ClientAppAPI Data is empty");
            coreRes.setElements(new ArrayList<>());
            return new ResponseEntity<>(coreRes, HttpStatus.OK);
        }
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/clientappapis/create")
    public ResponseEntity<CoreRes<ClientAppApi>> createClientAppApi(@RequestBody ClientAppApiReq app) {
        CoreRes<ClientAppApi> coreRes = clientAppApiService.createClientAppApi(app);
        return new ResponseEntity<>(coreRes, HttpStatus.CREATED);
    }

    @GetMapping("/clientappapis/get/app/{id}")
    public ResponseEntity<CoreRes<ClientAppApi>> getClientAppApiByAppId(@PathVariable(value = "id") UUID id) {
        CoreRes<ClientAppApi> coreRes = clientAppApiService.getClientAppApisByAppId(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @GetMapping("/clientappapis/get/client/{id}")
    public ResponseEntity<CoreRes<ClientAppApi>> getClientAppApisByClientId(@PathVariable(value = "id") UUID id) {
        CoreRes<ClientAppApi> coreRes = clientAppApiService.getClientAppApisByClientId(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @GetMapping("/clientappapis/get/api/{id}")
    public ResponseEntity<CoreRes<ClientAppApi>> getClientAppApisByAPIId(@PathVariable(value = "id") UUID id) {
        CoreRes<ClientAppApi> coreRes = clientAppApiService.getClientAppApisByAPIId(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/clientappapis/delete")
    public ResponseEntity<CoreRes<ClientAppApi>> deleteClientAppApi(@RequestBody ClientAppApiReq clientAppApiReq) {
        CoreRes<ClientAppApi> coreRes = clientAppApiService.deleteClientAppApi(clientAppApiReq);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }
}
