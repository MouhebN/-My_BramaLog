package com.bramaLog.Client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final EmailService emailService;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper, EmailService emailService) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.emailService = emailService;
    }

    public ClientResponseDTO addClient(ClientRequestDTO clientRequestDTO) {
        ClientEntity clientEntity = clientMapper.toEntity(clientRequestDTO);
        clientEntity = clientRepository.save(clientEntity);

        System.out.println("📧 Sending email to: " + clientEntity.getEmailPerso());
        System.out.println("👤 Client Name: " + clientEntity.getName());
        System.out.println("📨 Username: " + clientEntity.getEmail());
        System.out.println("🔒 Password: " + clientEntity.getPassword());

        emailService.sendCredentials(
                clientEntity.getEmailPerso(),
                clientEntity.getName(),
                clientEntity.getEmail(),
                clientEntity.getPassword()
        );

        return clientMapper.toResponseDto(clientEntity);
    }
}


