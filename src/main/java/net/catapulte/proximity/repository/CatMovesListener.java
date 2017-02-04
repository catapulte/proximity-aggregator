package net.catapulte.proximity.repository;

import net.catapulte.proximity.model.CatCrossPath;
import net.catapulte.proximity.model.CatMove;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.stereotype.Service;

@Service
public class CatMovesListener {

    private final CatPositionRepository positions;
    private final RabbitTemplate rabbit;

    @Autowired
    public CatMovesListener(CatPositionRepository positions, RabbitTemplate rabbit) {
        this.positions = positions;
        this.rabbit = rabbit;
    }

    @RabbitListener(queues = "cat.moves")
    public void process(CatMove cat) {
        positions.findByPositionWithin(new Circle(cat.getLatitude(), cat.getLongitude(), 20))
                .forEach(c -> rabbit.convertAndSend(new CatCrossPath(cat.getId(), c.getId())));
    }
}
