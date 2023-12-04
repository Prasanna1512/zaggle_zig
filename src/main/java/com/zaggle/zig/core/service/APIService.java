package com.zaggle.zig.core.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaggle.zig.core.domain.core.APIReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.Api;
import com.zaggle.zig.core.repository.APIRepo;
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
public class APIService {
    @Autowired
    APIRepo apiRepo;
    ObjectMapper mapper = new ObjectMapper();

    public CoreRes<Api> getAllAPIs() {
        CoreRes<Api> coreRes = new CoreRes<>();
        try {
            coreRes.setStatusCode(200);
            coreRes.setElements(new ArrayList<>(apiRepo.findAll()));
            coreRes.setMessage("Api Data Fetched Successfully");

        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Api> getAPIsById(UUID apiId) {
        CoreRes<Api> coreRes = new CoreRes<>();
        try {
            Optional<Api> api = apiRepo.findById(apiId);
            coreRes.setStatusCode(200);
            if (api.isPresent()) {
                List<Api> apis = new ArrayList<>();
                apis.add(api.get());
                coreRes.setElements(apis);
                coreRes.setMessage("Api found");
            } else {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("No Api Id matched with given Id");
            }
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Api> createAPI(APIReq apiReq) {
        CoreRes<Api> coreRes = new CoreRes<>();
        List<Api> apis = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            if (apiReq == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Please input the required fields");
                return coreRes;
            }
            if (apiReq.getName() == null || apiReq.getName().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field Name is required");
                return coreRes;
            }
            if (apiReq.getDomain() == null || apiReq.getDomain().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field Domain is required");
                return coreRes;
            }
            if (apiReq.getUri() == null || apiReq.getUri().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field URI is required");
                return coreRes;
            }
            if (apiReq.getMethod() == null || apiReq.getMethod().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field Method is required");
                return coreRes;
            }
            if (apiReq.getHeaders() == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field Headers is required");
                return coreRes;
            }

            Api api = new Api();
            api.setId(UUID.randomUUID());
            api.setName(apiReq.getName());
            api.setDescription(apiReq.getDescription() != null ? apiReq.getDescription() : "");
            api.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            api.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            api.setStatus(1);
            api.setDomain(apiReq.getDomain());
            api.setUri(apiReq.getUri());
            api.setMethod(apiReq.getMethod());

            ObjectMapper objectMapper = new ObjectMapper();
            String headersJson = objectMapper.writeValueAsString(apiReq.getHeaders());
            JsonNode node = objectMapper.readTree(headersJson);
            api.setHeaders(node);
            Api _api = apiRepo.save(api);
            apis.add(_api);
            coreRes.setElements(apis);
            coreRes.setMessage("Api created successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
            if (e.getMessage().equalsIgnoreCase("A JSONObject text must begin with '{' at 1 [character 2 line 1]")) {
                coreRes.setMessage("Headers must be a json object");
            }
        }
        return coreRes;
    }

    public CoreRes<Api> updateAPI(APIReq api) {
        CoreRes<Api> coreRes = new CoreRes<>();
        List<Api> apis = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            Api _api = apiRepo.findById(api.getId())
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("No API found with given ID = ", api.getId(), null));
            if(_api.getStatus() == -1 && api.getStatus() != 1) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("API is in inactive state. Please activate the API to update");
                return coreRes;
            }
            _api.setName((api.getName() != null && !api.getName().equalsIgnoreCase("")) ? api.getName() : _api.getName());
            _api.setDescription((api.getDescription() != null && !api.getDescription().equalsIgnoreCase("")) ? api.getDescription() : _api.getDescription());
            _api.setStatus((api.getStatus() == -1 || api.getStatus() ==1) ? api.getStatus() : _api.getStatus());
            _api.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            _api.setDomain((api.getDomain() != null && !api.getDomain().equalsIgnoreCase("")) ? api.getDomain() : _api.getDomain());
            _api.setUri((api.getUri() != null && !api.getUri().equalsIgnoreCase("")) ? api.getUri() : _api.getUri());
            _api.setMethod((api.getMethod() != null && !api.getMethod().equalsIgnoreCase("")) ? api.getMethod() : _api.getMethod());
            ObjectMapper objectMapper = new ObjectMapper();
            String headersJson = objectMapper.writeValueAsString(api.getHeaders() != null ? api.getHeaders() : _api.getHeaders());
            JsonNode node = objectMapper.readTree(headersJson);
            _api.setHeaders(node);
            Api updatedApi = apiRepo.save(_api);
            apis.add(updatedApi);
            coreRes.setElements(apis);
            coreRes.setMessage("Api details updated successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
            if (e.getMessage().equalsIgnoreCase("A JSONObject text must begin with '{' at 1 [character 2 line 1]")) {
                coreRes.setMessage("Headers must be a json object");
            }
        }
        return coreRes;
    }

    public CoreRes<Api> deleteAPI(UUID id) {
        CoreRes<Api> coreRes = new CoreRes<>();
        List<Api> apis = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            Api _api = apiRepo.findById(id)
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("No API found with given ID = ", id, null));
            apiRepo.deleteById(id);
            apis.add(_api);
            coreRes.setElements(apis);
            coreRes.setMessage("Api with given Id deleted successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Api> deleteAllAPIs() {
        CoreRes<Api> coreRes = new CoreRes<>();
        coreRes.setStatusCode(200);
        apiRepo.deleteAll();
        coreRes.setElements(new ArrayList<>());
        coreRes.setMessage("Apis deleted successfully");
        return coreRes;
    }
}
