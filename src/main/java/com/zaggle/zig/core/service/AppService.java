package com.zaggle.zig.core.service;

import com.zaggle.zig.core.domain.core.AppReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.App;
import com.zaggle.zig.core.repository.AppRepo;
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
public class AppService {

    @Autowired
    AppRepo appRepo;

    public CoreRes<App> getAllApps() {
        CoreRes<App> coreRes = new CoreRes<>();
        try {
            coreRes.setStatusCode(200);
            coreRes.setElements(new ArrayList<>(appRepo.findAll()));
            coreRes.setMessage("Apps Fetched Successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<App> getAppsById(UUID appId) {
        CoreRes<App> coreRes = new CoreRes<>();
        try {
            Optional<App> app = appRepo.findById(appId);
            coreRes.setStatusCode(200);
            if (app.isPresent()) {
                List<App> apps = new ArrayList<>();
                apps.add(app.get());
                coreRes.setElements(apps);
                coreRes.setMessage("App found");
            } else {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("No App Id matched with given Id");
            }
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;

    }

    public CoreRes<App> createApp(AppReq appReq) {
        CoreRes<App> coreRes = new CoreRes<>();
        List<App> apps = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            if (appReq == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Please input the required fields");
                return coreRes;
            }
            if (appReq.getName() == null || appReq.getName().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field Name is required");
                return coreRes;
            }

            App app = new App();
            app.setName(appReq.getName());
            app.setDescription(appReq.getDescription() != null ? appReq.getDescription() : "");
            app.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            app.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            app.setStatus(1);
            app.setSecretKey(appReq.getSecretKey() != null ? app.getSecretKey() : "");
            App _app = appRepo.save(app);

            apps.add(_app);
            coreRes.setElements(apps);
            coreRes.setMessage("App created successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<App> updateApp(AppReq app) {
        CoreRes<App> coreRes = new CoreRes<>();
        List<App> apps = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            App _app = appRepo.findById(app.getId())
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("No App found with given id = ", app.getId(), null));

            if (_app.getStatus() == -1 && app.getStatus() != 1) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("App is in inactive state. Please activate the app to update");
                return coreRes;
            }
            _app.setName((app.getName() != null && !app.getName().equalsIgnoreCase("")) ? app.getName() : _app.getName());
            _app.setDescription((app.getDescription() != null && !app.getDescription().equalsIgnoreCase("")) ? app.getDescription() : _app.getDescription());
            _app.setStatus((app.getStatus() == -1 || app.getStatus() ==1) ? app.getStatus() : _app.getStatus());
            _app.setSecretKey(app.getSecretKey() != null ? app.getSecretKey() : _app.getSecretKey());
            _app.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            App updatedApp = appRepo.save(_app);
            apps.add(updatedApp);
            coreRes.setElements(apps);
            coreRes.setMessage("App details updated successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<App> deleteApp(UUID id) {
        CoreRes<App> coreRes = new CoreRes<>();
        List<App> apps = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            App _app = appRepo.findById(id)
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("No App found with given ID = ", id, null));
            appRepo.deleteById(id);
            apps.add(_app);
            coreRes.setElements(apps);
            coreRes.setMessage("App with given Id deleted successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<App> deleteAllApps() {
        CoreRes<App> coreRes = new CoreRes<>();
        coreRes.setStatusCode(200);
        appRepo.deleteAll();
        coreRes.setElements(new ArrayList<>());
        coreRes.setMessage("Apps deleted successfully");
        return coreRes;
    }
}
