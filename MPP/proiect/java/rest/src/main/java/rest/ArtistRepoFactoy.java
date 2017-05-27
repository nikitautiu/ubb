package rest;

import model.Artist;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.*;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by vitiv on 4/24/17.
 */
@Configuration
public class ArtistRepoFactoy {

    @Bean
    public static ICrudRepository<Artist, Integer> artistRepoFactory() {
        Properties serverProps = new Properties();
        try {
            serverProps.load(new FileReader("server.config"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find properties " + e);
        }

       return new ArtistSqlRepo(serverProps);
    }
}
