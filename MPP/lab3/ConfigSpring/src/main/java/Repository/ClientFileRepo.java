package Repository;

import Domain.Client;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * File Client repository
 * Implements the same interface as the memory one, but
 * it save changes to a file
 */
public class ClientFileRepo extends ClientRepo {
    private String filename;

    /**
     * File repo constructor
     * @param filename The name of the file which to load changes from,
     *                 and save to.
     */
    public ClientFileRepo(String filename) {
        this.filename = filename;
        loadFromFile();  // load the data
    }

    @Override
    public void add(Client movie) {
        super.add(movie);
        saveToFile();  // save the changes
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
        saveToFile();  // save the changes
    }

    /**
     * Flushes the content of the repository to a file
     */
    private void saveToFile() {
        try {
            PrintStream out = new PrintStream(new FileOutputStream(this.filename));
            for(Client client : this.entities)
                out.printf("%d|%s\n", client.getId(), client.getName());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads entities content from file
     */
    private void loadFromFile() {
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.forEach(line -> {
                String[] args = line.split("\\|");
                if(args.length != 2)
                    throw new RepositoryException("Corrupted data");
                try {
                    Client client = new Client(Integer.parseInt(args[0]), args[1]);
                    this.add(client);
                } catch(Exception e) {
                    throw new RepositoryException("Corrupted data");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
