package com.zaggle.zig.core.controller;

import com.zaggle.zig.core.domain.core.AppReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.App;
import com.zaggle.zig.core.service.AppService;
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
public class AppController {

    @Autowired
    AppService appService;

    @GetMapping("/apps/get")
    public ResponseEntity<CoreRes<App>> getAllApps() {
        CoreRes<App> coreRes = appService.getAllApps();
        if (coreRes.getElements().isEmpty()) {
            coreRes.setStatusCode(200);
            coreRes.setMessage("App Data is empty");
            coreRes.setElements(new ArrayList<>());
            return new ResponseEntity<>(coreRes, HttpStatus.OK);
        }
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @GetMapping("/apps/get/{id}")
    public ResponseEntity<CoreRes<App>> getAppsById(@PathVariable(value = "id") UUID id) {
        CoreRes<App> coreRes = appService.getAppsById(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/apps/create")
    public ResponseEntity<CoreRes<App>> createApp(@RequestBody AppReq app) {
        CoreRes<App> coreRes = appService.createApp(app);
        return new ResponseEntity<>(coreRes, HttpStatus.CREATED);
    }

    @PutMapping("/apps/update")
    public ResponseEntity<CoreRes<App>> updateApp(@RequestBody AppReq appRequest) {
        CoreRes<App> coreRes = appService.updateApp(appRequest);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @DeleteMapping("/apps/delete/{id}")
    public ResponseEntity<CoreRes<App>> deleteApp(@PathVariable("id") UUID id) {
        CoreRes<App> coreRes = appService.deleteApp(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @DeleteMapping("/apps/delete")
    public ResponseEntity<CoreRes<App>> deleteAllApps() {
        CoreRes<App> coreRes = appService.deleteAllApps();
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

}
