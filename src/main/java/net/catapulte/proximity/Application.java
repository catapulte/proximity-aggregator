package net.catapulte.proximity;

import com.mongodb.MongoClientURI;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ConnectionFactory rabbitmqConnectionFactory(@Value("${amqp.host:localhost}") String host,
                                                       @Value("${amqp.port:5672}") int port,
                                                       @Value("${amqp.host:guest}") String user,
                                                       @Value("${amqp.host:guest}") String pass) {
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setHost(host);
        cf.setPort(port);
        cf.setUsername(user);
        cf.setPassword(pass);
        return cf;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory rabbitmqConnectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(rabbitmqConnectionFactory);
        admin.declareQueue(new Queue("cat.crosspath"));
        return admin;
    }

    @Bean
    public MongoDbFactory mongoDbFactory(@Value("${mongo.host:localhost}") String host,
                                  @Value("${mongo.port:27017}") int port,
                                  @Value("${mongo.user:guest}") String user,
                                  @Value("${mongo.pass:guest}") String pass) throws UnknownHostException {
        MongoClientURI uri = new MongoClientURI(String.format("mongodb://%s:%s@%s:%d/cat", user, pass, host, port));
        SimpleMongoDbFactory f = new SimpleMongoDbFactory(uri);
        return f;
    }
}
