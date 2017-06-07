package rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/logs/")
public class RestService {
    @Autowired
    private ICrudRepository<Artist, Integer> artistRepo;



    @RequestMapping(method=RequestMethod.GET)
    public Log[] getAll(){
        Collection<Artist> artists = artistRepo.getAll();
        return artists.toArray(new Artist[0]); // convert to array
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Integer id){

        Artist artist= artistRepo.find(id);
        if (artist==null)
            return new ResponseEntity<>("Artist not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        Artist artist = artistRepo.find(id);
        if (artist==null)
            return new ResponseEntity<>("Artist not found", HttpStatus.NOT_FOUND);
        else {
            artistRepo.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Artist artist){
        artistRepo.add(artist);
        return new ResponseEntity<>(artist, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Artist update(@RequestBody Artist artist, @PathVariable Integer id) {
        System.out.println("Updating user ...");
        artist.setId(id);   // set it to the id
        artistRepo.add(artist);
        return artist;

    }


    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String artistError(RepositoryException e) {
        return e.getMessage();
    }
}