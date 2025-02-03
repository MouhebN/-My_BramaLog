package com.bramaLog.Client;

import java.util.UUID;

public record ClientDTO(UUID id ,
                        String name ,
                        String email) {}
