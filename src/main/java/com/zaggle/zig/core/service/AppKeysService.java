package com.zaggle.zig.core.service;

import com.zaggle.zig.core.domain.core.AppKeysReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.AppKeys;
import com.zaggle.zig.core.repository.AppKeysRepo;
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
public class AppKeysService {

    @Autowired
    AppKeysRepo appKeysRepo;

    public CoreRes<AppKeys> getAllAppKeys() {
        CoreRes<AppKeys> coreRes = new CoreRes<>();
        try {
            coreRes.setStatusCode(200);
            coreRes.setElements(new ArrayList<>(appKeysRepo.findAll()));
            coreRes.setMessage("AppKeys Fetched Successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<AppKeys> getAppKeysById(UUID appKeysId) {
        CoreRes<AppKeys> coreRes = new CoreRes<>();
        try {
            Optional<AppKeys> appKeys = appKeysRepo.findById(appKeysId);
            coreRes.setStatusCode(200);
            if (appKeys.isPresent()) {
                List<AppKeys> appKeysList = new ArrayList<>();
                appKeysList.add(appKeys.get());
                coreRes.setElements(appKeysList);
                coreRes.setMessage("AppKey found");
            } else {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("No AppKey Id matched with given Id");
            }
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;

    }

    public CoreRes<AppKeys> createAppKeys(AppKeysReq appKeysReq) {
        CoreRes<AppKeys> coreRes = new CoreRes<>();
        List<AppKeys> appKeysList = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            if (appKeysReq == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Please input the required fields");
                return coreRes;
            }
            if (appKeysReq.getKeyName() == null || appKeysReq.getKeyName().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field KeyName is required");
                return coreRes;
            }
            if (appKeysReq.getKeyValue() == null || appKeysReq.getKeyValue().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field KeyValue is required");
                return coreRes;
            }

            AppKeys appKeys = new AppKeys();
            appKeys.setId(UUID.randomUUID());
            appKeys.setKeyName(appKeysReq.getKeyName());
            appKeys.setKeyValue(appKeysReq.getKeyValue());
            appKeys.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            appKeys.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            appKeys.setStatus(1);
            AppKeys _appKeys = appKeysRepo.save(appKeys);

            appKeysList.add(_appKeys);
            coreRes.setElements(appKeysList);
            coreRes.setMessage("AppKeys created successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<AppKeys> updateAppKeys(UUID id, AppKeysReq appKeysReq) {
        CoreRes<AppKeys> coreRes = new CoreRes<>();
        List<AppKeys> appKeysList = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            AppKeys _appKeys = appKeysRepo.findById(id)
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("No AppKeys found with given id = ", id, null));

            if (_appKeys.getStatus() == -1 && appKeysReq.getStatus() != 1) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("AppKey is in inactive state. Please activate the appkey to update");
                return coreRes;
            }
            _appKeys.setKeyName((appKeysReq.getKeyName() != null && !appKeysReq.getKeyName().equalsIgnoreCase("")) ? appKeysReq.getKeyName() : _appKeys.getKeyName());
            _appKeys.setKeyValue((appKeysReq.getKeyValue() != null && !appKeysReq.getKeyValue().equalsIgnoreCase("")) ? appKeysReq.getKeyValue() : _appKeys.getKeyValue());
            _appKeys.setStatus((appKeysReq.getStatus() == -1 || appKeysReq.getStatus() ==1) ? appKeysReq.getStatus() : _appKeys.getStatus());
            _appKeys.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            AppKeys updatedAppKeys = appKeysRepo.save(_appKeys);
            appKeysList.add(updatedAppKeys);
            coreRes.setElements(appKeysList);
            coreRes.setMessage("AppKeys details updated successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<AppKeys> deleteAppKeys(UUID id) {
        CoreRes<AppKeys> coreRes = new CoreRes<>();
        List<AppKeys> appKeysList = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            AppKeys _appKeys = appKeysRepo.findById(id)
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("No AppKeys found with given id = ", id, null));

            appKeysRepo.deleteById(id);
            appKeysList.add(_appKeys);
            coreRes.setElements(appKeysList);
            coreRes.setMessage("AppKeys with given Id deleted successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<AppKeys> deleteAllAppKeys() {
        CoreRes<AppKeys> coreRes = new CoreRes<>();
        coreRes.setStatusCode(200);
        appKeysRepo.deleteAll();
        coreRes.setElements(new ArrayList<>());
        coreRes.setMessage("AppKeys deleted successfully");
        return coreRes;
    }

}
