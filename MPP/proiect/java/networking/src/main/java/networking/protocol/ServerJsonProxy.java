package networking.protocol;

import com.google.gson.*;
import model.Purchase;
import model.ShowData;
import services.ServiceException;
import services.IClientService;
import services.IServerService;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ServerJsonProxy implements IServerService {
    private String host;
    private int port;

    private IClientService client;

    private BufferedReader input;
    private OutputStreamWriter output;
    private Socket connection;

    //private List<Response> responses;
    private BlockingQueue<JsonObject> qresponses;
    private volatile boolean finished;

    public ServerJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        //responses=new ArrayList<Response>();
        qresponses= new LinkedBlockingQueue<>();
        initializeConnection();
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(JsonObject request) {
        try {
            Gson builder = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
            request.addProperty("type", "request");
            output.write(builder.toJson(request) + '\n');
            output.flush();
        } catch (IOException e) {
            throw new ServiceException("Error sending object " + e);
        }

    }

    private JsonObject readResponse()  {
        JsonObject response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection()  {
         try {
            connection=new Socket(host,port);
            output = new OutputStreamWriter(
                     connection.getOutputStream(), StandardCharsets.UTF_8);
            output.flush();
            input =  new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), StandardCharsets.UTF_8));
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(JsonObject response) {
        if(Objects.equals(response.get("name").getAsString(), "changesOccurred")) {
            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
            ArrayList<ShowData> shows = new ArrayList<>();
            for(JsonElement elem : response.get("shows").getAsJsonArray()) {
                JsonObject object = elem.getAsJsonObject();
                int id = object.get("id").getAsInt();
                String artistName = object.get("artistName").getAsString();
                String locationName = object.get("locationName").getAsString();
                Date startTime = gson.fromJson(object.get("startTime"), Date.class);
                int remainingSeats = object.get("remainingSeats").getAsInt();


                shows.add(new ShowData(id, artistName, locationName, startTime, remainingSeats));
            }
            client.changesOccurred(shows);
        }
    }

    @Override
    public void addPurchase(Purchase entity)  {
        JsonObject request = new JsonObject();
        request.addProperty("name", "addPurchase");
        request.addProperty("clientName", entity.getClientName());
        request.addProperty("showId", entity.getShowId());
        request.addProperty("quantity", entity.getQuantity());

        sendRequest(request);
        JsonObject response=readResponse();

        if (Objects.equals(response.get("type").getAsString(), "error")){
            throw new ServiceException(response.get("message").getAsString());
        }
    }

    @Override
    public Collection<ShowData> getAll()  {
        JsonObject request = new JsonObject();
        request.addProperty("name", "getAll");

        sendRequest(request);
        JsonObject response = readResponse();

        ArrayList<ShowData> shows = new ArrayList<>();

        if (Objects.equals(response.get("type").getAsString(), "error")){
            closeConnection();
            throw new ServiceException(response.get("message").getAsString());
        }
        else {

            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
            for(JsonElement elem : response.get("shows").getAsJsonArray()) {
                JsonObject object = elem.getAsJsonObject();
                int id = object.get("id").getAsInt();
                String artistName = object.get("artistName").getAsString();
                String locationName = object.get("locationName").getAsString();
                Date startTime = gson.fromJson(object.get("startTime"), Date.class);
                int remainingSeats = object.get("remainingSeats").getAsInt();

                shows.add(new ShowData(id, artistName, locationName, startTime, remainingSeats));
            }
        }
        return shows;
    }

    @Override
    public boolean login(String username, String password, IClientService client)  {

        JsonObject request = new JsonObject();
        request.addProperty("name", "login");
        request.addProperty("username", username);
        request.addProperty("password", password);

        sendRequest(request);
        JsonObject response=readResponse();

        if (Objects.equals(response.get("type").getAsString(), "response")){
            if(response.get("success").getAsBoolean()) {
                this.client = client;
                return true;
            }
            return false;
        }
        if (Objects.equals(response.get("type").getAsString(), "error")){
            closeConnection();
            throw new ServiceException(response.get("message").getAsString());
        }
        return false;
    }

    @Override
    public void logout(IClientService clientService) {
        this.client = null;
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
        JsonObject request = new JsonObject();
        request.addProperty("name", "logout");
        sendRequest(request);
        JsonObject response=readResponse();
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    String str = input.readLine();
                    System.out.println("response received " + str);

                    JsonObject parsed = new JsonParser().parse(str).getAsJsonObject();
                    if(Objects.equals(parsed.get("type").getAsString(), "update")){
                         handleUpdate(parsed);
                    } else {
                        try {
                            qresponses.put(parsed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
