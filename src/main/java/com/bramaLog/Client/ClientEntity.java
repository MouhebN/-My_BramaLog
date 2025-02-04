package com.bramaLog.Client;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Document(collection = "clients")
@Getter
@Setter
public class ClientEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    private String name;
    private String emailPerso;
    private String email;
    private String password;

}
