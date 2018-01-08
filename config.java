package PerformanceMonitoring;

import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@ComponentScan
@Configuration
@EnableAutoConfiguration
public class config
{
    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception
    {

        MongoClient mongoClient = new MongoClient("localhost",27017);  //52.8.70.178 //localhost
        // MongoClient mongoClient = new MongoClient("ds061621.mongolab.com",61621);
        UserCredentials userCredentials = new UserCredentials("","");
        return new SimpleMongoDbFactory(mongoClient, "stats",userCredentials) ;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception
    {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }
}
