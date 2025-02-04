package com.bramaLog.Product;

import java.util.List;
import java.util.UUID;

public record CreateProductResponse(UUID id,
                                    String name,
                                    String description,
                                    List<PbiDTO> backlog,
                                    EstimationDTO estimation
) {
    public record PbiDTO(
            UUID id,
            String title,
            String description,
            int priority,
            int storyPoints,
            double estimatedCost,
            double businessValue,
            List<String> acceptanceCriteria,
            PbiStatus status
    ) {}

    public record EstimationDTO(
            double totalCost,
            String totalDuration
    ) {}
}
