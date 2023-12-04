package com.zaggle.zig.core.service;

import com.zaggle.zig.core.domain.core.ClientReq;
import com.zaggle.zig.core.domain.core.CoreRes;
import com.zaggle.zig.core.entity.Client;
import com.zaggle.zig.core.repository.ClientRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
public class ClientService {

    @Autowired
    ClientRepo clientRepo;

    public CoreRes<Client> getAllClients() {
        CoreRes<Client> coreRes = new CoreRes<>();
        try {
            coreRes.setStatusCode(200);
            coreRes.setElements(new ArrayList<>(clientRepo.findAll()));
            coreRes.setMessage("Client Data Fetched Successfully");

        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Client> getClientById(UUID id) {
        CoreRes<Client> coreRes = new CoreRes<>();
        try {
            Optional<Client> client = clientRepo.findById(id);
            coreRes.setStatusCode(200);
            if (client.isPresent()) {
                List<Client> clients = new ArrayList<>();
                clients.add(client.get());
                coreRes.setElements(clients);
                coreRes.setMessage("Client found");
            } else {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("No Client Id matched with given Id");
            }
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Client> createClient(ClientReq clientReq) {
        CoreRes<Client> coreRes = new CoreRes<>();
        List<Client> clients = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            if (clientReq == null) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Please input the required fields");
                return coreRes;
            }
            if (clientReq.getName() == null || clientReq.getName().equalsIgnoreCase("")) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Field Name is required");
                return coreRes;
            }

            Client client = new Client();
            client.setName(clientReq.getName());
            client.setDescription(clientReq.getDescription() != null ? clientReq.getDescription() : "");
            client.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            client.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            client.setStatus(1);
            client.setClientSecret(convertSecretKeyToString(generateKey(256)));
            client.setAssociatedData(convertIVToString(createIV(256)));
            Client _client = clientRepo.save(client);

            clients.add(_client);
            coreRes.setMessage("Client created successfully");
            coreRes.setElements(clients);
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            log.info(e.getMessage());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;

    }

    public CoreRes<Client>  updateClient(ClientReq client) {
        CoreRes<Client> coreRes = new CoreRes<>();
        List<Client> clients = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            Client _client = clientRepo.findById(client.getId())
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("Not found Client with id = " ,client.getId(), null));
            if (_client.getStatus() == -1 && client.getStatus() != 1) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Client is in inactive state. Please activate the client to update");
                return coreRes;
            }
            _client.setName(client.getName() != null ? client.getName() : _client.getName());
            _client.setDescription(client.getDescription() != null ? client.getDescription() : _client.getDescription());
            _client.setStatus((client.getStatus() == -1 || client.getStatus() == 1) ? client.getStatus() : _client.getStatus());
            _client.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Client updatedClient = clientRepo.save(_client);
            clients.add(updatedClient);
            coreRes.setElements(clients);
            coreRes.setMessage("Client details updated successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Client> deleteClient(UUID id) {
        CoreRes<Client> coreRes = new CoreRes<>();
        List<Client> apps = new ArrayList<>();
        try {
            coreRes.setStatusCode(200);
            Client _client = clientRepo.findById(id)
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("No Client found with given ID = ", id, null));
            clientRepo.deleteById(id);
            apps.add(_client);
            coreRes.setElements(apps);
            coreRes.setMessage("Client with given Id deleted successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Client> deleteAllClients() {
        CoreRes<Client> coreRes = new CoreRes<>();
        coreRes.setStatusCode(200);
        clientRepo.deleteAll();
        coreRes.setElements(new ArrayList<>());
        coreRes.setMessage("Clients deleted successfully");
        return coreRes;
    }

    public SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    //both above and this method can be useful for generation of secret key.
    public SecretKey generateKeyUsingSecureRandom() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }

    public byte[] createIV(final int ivSizeBytes) {
        byte[] iv = new byte[ivSizeBytes/8];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        return ivspec.getIV();
    }

    public static String convertIVToString( byte[] IV) {
        //byte[] rawData = secretKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(IV);
        return encodedKey;
    }

    public static String convertSecretKeyToString(SecretKey secretKey) {
        byte[] rawData = secretKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(rawData);
        return encodedKey;
    }

    public CoreRes<Client> updateClientSecret(UUID id) {
        CoreRes<Client> coreRes = new CoreRes<>();
        List<Client> clients = new ArrayList<>();
        try{
            coreRes.setStatusCode(200);
            Client _client = clientRepo.findById(id)
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("Not found Client with id = " ,id, null));
            if (_client.getStatus() == -1) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Client is in inactive state. Please activate the client to update");
                return coreRes;
            }

            _client.setClientSecret(convertSecretKeyToString(generateKey(256)));

            Client updatedClient = clientRepo.save(_client);
            clients.add(updatedClient);
            coreRes.setElements(clients);
            coreRes.setMessage("Client secret updated successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Client> updateAssociatedData(UUID id) {
        CoreRes<Client> coreRes = new CoreRes<>();
        List<Client> clients = new ArrayList<>();
        try{
            coreRes.setStatusCode(200);
            Client _client = clientRepo.findById(id)
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("Not found Client with id = " ,id, null));
            if (_client.getStatus() == -1) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Client is in inactive state. Please activate the client to update");
                return coreRes;
            }

            _client.setAssociatedData(convertIVToString(createIV(256)));

            Client updatedClient = clientRepo.save(_client);
            clients.add(updatedClient);
            coreRes.setElements(clients);
            coreRes.setMessage("Client associated data updated successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Client> updateSignatureSecret(UUID id) {
        CoreRes<Client> coreRes = new CoreRes<>();
        List<Client> clients = new ArrayList<>();
        try{
            coreRes.setStatusCode(200);
            Client _client = clientRepo.findById(id)
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("Not found Client with id = " ,id, null));
            if (_client.getStatus() == -1) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Client is in inactive state. Please activate the client to update");
                return coreRes;
            }

            _client.setSignatureSecret(convertSecretKeyToString(generateKeyUsingSecureRandom()));

            Client updatedClient = clientRepo.save(_client);
            clients.add(updatedClient);
            coreRes.setElements(clients);
            coreRes.setMessage("Client associated data updated successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

    public CoreRes<Client> updateSignature(String id, String secret) {
        CoreRes<Client> coreRes = new CoreRes<>();
        List<Client> clients = new ArrayList<>();
        try{
            coreRes.setStatusCode(200);
            Client _client = clientRepo.findById(UUID.fromString(id))
                    .orElseThrow(() -> new InvalidConfigurationPropertyValueException("Not found Client with id = " ,id, null));
            if (_client.getStatus() == -1) {
                coreRes.setElements(new ArrayList<>());
                coreRes.setMessage("Client is in inactive state. Please activate the client to update");
                return coreRes;
            }

            _client.setSignatureSecret(secret);

            Client updatedClient = clientRepo.save(_client);
            clients.add(updatedClient);
            coreRes.setElements(clients);
            coreRes.setMessage("Client associated data updated successfully");
        } catch (Exception e) {
            coreRes.setElements(new ArrayList<>());
            coreRes.setMessage(e.getMessage());
        }
        return coreRes;
    }

}
