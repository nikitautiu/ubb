package Repository;

import java.io.*;
import java.util.ArrayList;

import Domain.Movie;


/**
 * Same as the in-memory movie repository,
 * but saves changes to a file. Uses serialization.
 */
public class MovieFileRepo extends MovieRepo {
    private String filename;

    /**
     * File repo constructor
     * @param filename The name of the file which to load changes from,
     *                 and save to.
     */
    public MovieFileRepo(String filename) {
        this.filename = filename;
        loadFromFile();  // load the data
    }

    @Override
    public void add(Movie movie) {
        super.add(movie);
        saveToFile();  // save the changes
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
        saveToFile();  // save the changes
    }

    /**
     * Loads the repository data from the given file.
     * Uses serialization.
     */
    private void saveToFile() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(this.entities);
        } catch(IOException exc) {
            exc.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch(IOException exc) {
                exc.printStackTrace();
            }
        }
    }

    /**
     * Saves the repository data to the specified file
     * Uses serialization for this.
     */
    private void loadFromFile() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(filename));
            // add all to the repo
            this.entities = (ArrayList<Movie>) in.readObject();
        } catch(IOException exc) {
            exc.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RepositoryException("Corrupted file.");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException exc) {
                    throw new RepositoryException("Corrupted file.");
                }
            }
        }
    }
}
