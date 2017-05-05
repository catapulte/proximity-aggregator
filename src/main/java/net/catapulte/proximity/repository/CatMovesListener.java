package net.catapulte.proximity.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.catapulte.proximity.model.CatCrossPath;
import net.catapulte.proximity.model.CatMove;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CatMovesListener {

    private final CatPositionRepository positions;
    private final RabbitTemplate rabbit;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CatMovesListener(CatPositionRepository positions, RabbitTemplate rabbit) {
        this.positions = positions;
        this.rabbit = rabbit;
    }

    @RabbitListener(queues = "cat.moves")
    public void process(byte[] serializedCat) throws IOException {
        CatMove cat = objectMapper.readValue(serializedCat, CatMove.class);
        positions.findByIdNotAndPositionWithin(cat.getId(), new Circle(new Point(cat.getLatitude(), cat.getLongitude()), new Distance(20, Metrics.KILOMETERS)))
                .forEach(c -> rabbit.convertAndSend(new CatCrossPath(cat.getId(), c.getId())));
    }
}
