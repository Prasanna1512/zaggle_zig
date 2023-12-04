package com.zaggle.zig.core.controller;
import com.zaggle.zig.core.domain.core.APIReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.Api;
import com.zaggle.zig.core.service.APIService;
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
public class APIController {

    @Autowired
    APIService apiService;

    @GetMapping("/apis/get")
    public ResponseEntity<CoreRes<Api>> getAllAPIs() {
        CoreRes<Api> coreRes = apiService.getAllAPIs();
        if (coreRes.getElements().isEmpty()) {
            coreRes.setStatusCode(200);
            coreRes.setMessage("API Data is empty");
            coreRes.setElements(new ArrayList<>());
            return new ResponseEntity<>(coreRes, HttpStatus.OK);
        }
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @GetMapping("/apis/get/{id}")
    public ResponseEntity<CoreRes<Api>> getAPIbyId(@PathVariable("id") UUID id) {
        CoreRes<Api> coreRes = apiService.getAPIsById(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @PostMapping("/apis/create")
    public ResponseEntity<CoreRes<Api>> createAPI(@RequestBody APIReq api) {
        CoreRes<Api> coreRes = apiService.createAPI(api);
        return new ResponseEntity<>(coreRes, HttpStatus.CREATED);
    }

    @PutMapping("/apis/update")
    public ResponseEntity<CoreRes<Api>> updateAPI(@RequestBody APIReq api) {
        CoreRes<Api> coreRes = apiService.updateAPI(api);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @DeleteMapping("/apis/delete/{id}")
    public ResponseEntity<CoreRes<Api>> deleteAPI(@PathVariable("id") UUID id) {
        CoreRes<Api> coreRes = apiService.deleteAPI(id);
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

    @DeleteMapping("/apis/delete")
    public ResponseEntity<CoreRes<Api>> deleteAllAPIs() {
        CoreRes<Api> coreRes = apiService.deleteAllAPIs();
        return new ResponseEntity<>(coreRes, HttpStatus.OK);
    }

}
