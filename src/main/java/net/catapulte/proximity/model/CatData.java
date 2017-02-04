package net.catapulte.proximity.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "catdata")
public class CatData {

    @Id
    private String id;

    private double[] position;

    private double angle;

    private long timestamp;

    private double vbat;

    private int satelliteNb;

    private double altitude;

    private double temperature;

    private double humidity;
}
