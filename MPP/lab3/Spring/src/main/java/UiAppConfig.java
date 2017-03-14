import Repository.*;
import Controller.Controller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UiAppConfig {

    @Bean(name="clientRepo")
    public ClientRepo createClientRepo(){
        return new ClientFileRepo("clients.txt");
    }

    @Bean(name="movieRepo")
    public MovieRepo createMovieRepo(){
        return new MovieFileRepo("movies.txt");
    }

    @Bean(name="rentalRepo")
    public RentalRepo createRentalRepo(){
        return new RentalXMLRepo("rentals.xml");
    }


    @Bean(name="controller")
    public Controller taskService(ClientRepo clientRepo, MovieRepo movieRepo, RentalRepo rentalRepo){
        return new Controller(movieRepo, clientRepo, rentalRepo);
    }

}
