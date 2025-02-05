package com.bramaLog.Product;

import java.util.List;

public record UpdatePbiRequest(
        String title,
        String description,
        Integer priority,
        Integer storyPoints,
        Double estimatedCost,
        Double businessValue,
        List<String> acceptanceCriteria,
        PbiStatus status
) {}

