package com.bramaLog.Product;

import java.util.List;
import java.util.UUID;

public record CreateProductResponse(String id,
                                    String name,
                                    String description,
                                    List<PbiDTO> backlog,
                                    EstimationDTO estimation
) {
    public record PbiDTO(
            String id,
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
