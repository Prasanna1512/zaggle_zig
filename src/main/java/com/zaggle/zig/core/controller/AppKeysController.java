package com.zaggle.zig.core.controller;

import com.zaggle.zig.core.domain.core.AppKeysReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.AppKeys;
import com.zaggle.zig.core.service.AppKeysService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/core")
@CrossOrigin
public class AppKeysController {

    @Autowired
    AppKeysService appKeysService;

    @GetMapping("/appkeys/get")
    public ResponseEntity<CoreRes<AppKeys>> getAllAppKeys() {
        CoreRes<AppKeys> coreRes = appKeysService.getAllAppKeys();
        if (coreRes.getElements().isEmpty()) {
            coreRes.setStatusCode(200);
            coreRes.setMessage("App Data is empty");
            coreRes.setElements(new ArrayList<>());
            return new ResponseEntity<>(coreRes, HttpStatus.OK);
        }
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @GetMapping("/appkeys/get/{id}")
    public ResponseEntity<CoreRes<AppKeys>> getAppKeysById(@PathVariable(value = "id") UUID id) {
        CoreRes<AppKeys> coreRes = appKeysService.getAppKeysById(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/appkeys/create")
    public ResponseEntity<CoreRes<AppKeys>> createAppKeys(@RequestBody AppKeysReq appKeysReq) {
        CoreRes<AppKeys> coreRes = appKeysService.createAppKeys(appKeysReq);
        return new ResponseEntity<>(coreRes, HttpStatus.CREATED);
    }

    @PutMapping("/appkeys/update/{id}")
    public ResponseEntity<CoreRes<AppKeys>> updateAppKeys(@PathVariable("id") UUID id, @RequestBody AppKeysReq appKeysRequest) {
        CoreRes<AppKeys> coreRes = appKeysService.updateAppKeys(id, appKeysRequest);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @DeleteMapping("/appkeys/delete/{id}")
    public ResponseEntity<CoreRes<AppKeys>> deleteAppKeys(@PathVariable("id") UUID id) {
        CoreRes<AppKeys> coreRes = appKeysService.deleteAppKeys(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @DeleteMapping("/appkeys/delete")
    public ResponseEntity<CoreRes<AppKeys>> deleteAllAppKeys() {
        CoreRes<AppKeys> coreRes = appKeysService.deleteAllAppKeys();
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

}
