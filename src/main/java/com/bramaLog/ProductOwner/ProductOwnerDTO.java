package com.bramaLog.ProductOwner;

import java.util.UUID;

public record ProductOwnerDTO
        (UUID id ,
         String name ,
         String email ,
         String password) {}
