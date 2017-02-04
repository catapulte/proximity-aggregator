package net.catapulte.proximity.repository;

import net.catapulte.proximity.model.CatData;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CatPositionRepository extends MongoRepository<CatData, String> {

    List<CatData> findByPositionWithin(Circle c);
}
