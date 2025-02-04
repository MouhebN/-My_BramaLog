package com.bramaLog.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> addClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        ClientResponseDTO savedClient = clientService.addClient(clientRequestDTO);
        return ResponseEntity.ok(savedClient);
    }
}
