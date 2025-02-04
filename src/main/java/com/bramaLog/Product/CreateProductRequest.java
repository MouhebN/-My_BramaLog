package com.bramaLog.Product;

import java.util.List;

public record CreateProductRequest(String name,
                                   String description,
                                   List<PbiDTO> backlog
) {
    public record PbiDTO(
            String title,
            String description,
            int priority,
            int storyPoints,
            double estimatedCost,
            double businessValue,
            List<String> acceptanceCriteria,
            PbiStatus status
    ) {}
}

