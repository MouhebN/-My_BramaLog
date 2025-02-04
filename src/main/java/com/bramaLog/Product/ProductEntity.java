package com.bramaLog.Product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "Products")
@Getter
@Setter
public class ProductEntity {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String description;

    private List<Pbi> backlog = new ArrayList<>();
    private Estimation estimation;


    @Getter
    @Setter
    public static class Pbi {
        private UUID id = UUID.randomUUID();
        private String title;
        private String description;
        private int priority;
        private int storyPoints;
        private double estimatedCost;
        private double businessValue;
        private List<String> acceptanceCriteria = new ArrayList<>();
        private PbiStatus status = PbiStatus.A_DISCUTER;

    }
    @Getter
    @Setter
    public static class Estimation {
        private double totalCost;
        private String totalDuration;
    }
}
