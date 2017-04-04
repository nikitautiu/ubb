package networking.protocol;


import com.google.gson.*;
import model.Purchase;
import model.ShowData;
import services.IClientService;
import services.IServerService;
import services.ServiceException;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Objects;


public class ClientJsonWorker implements Runnable, IClientService {
    private IServerService server;
    private Socket connection;

    private BufferedReader input;
    private OutputStreamWriter output;
    private volatile boolean connected;
    public ClientJsonWorker(IServerService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output = new OutputStreamWriter(
                    connection.getOutputStream(), StandardCharsets.UTF_8);
            output.flush();
            input =  new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), StandardCharsets.UTF_8));
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                String str = input.readLine();
                JsonObject parsed = new JsonParser().parse(str).getAsJsonObject();
                JsonObject response = handleRequest(parsed);
                if (response!=null){
                   sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private JsonObject handleRequest(JsonObject request){
        if (Objects.equals(request.get("name").getAsString(), "login")){
            System.out.println("Login request ...");

            String username = request.get("username").getAsString();
            String password = request.get("password").getAsString();

            try {
                boolean status = server.login(username, password, this);
                JsonObject response = new JsonObject();
                response.addProperty("type", "response");
                response.addProperty("success", status);
                return response;
            } catch (ServiceException e) {
                connected=false;
                return getErrorResponse(e);
            }
        }
        if (Objects.equals(request.get("name").getAsString(), "addPurchase")){
            return addPurchase(request);
        }
        if (Objects.equals(request.get("name").getAsString(), "getAll")){
            return getAll();
        }
        return null;
    }

    private JsonObject addPurchase(JsonObject request) {
        System.out.println("AddPurchase request ...");
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();

        int showId = request.get("showId").getAsInt();
        String clientName = request.get("clientName").getAsString();
        int quantity = request.get("quantity").getAsInt();

        try {
            server.addPurchase(new Purchase(0, showId, clientName, quantity));
            JsonObject response = new JsonObject();
            response.add("type", gson.toJsonTree("response"));
            return response;
        } catch (ServiceException e) {
            return  getErrorResponse(e);
        }
    }

    private JsonObject getAll() {
        System.out.println("Get all request ...");
        try {
            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
            JsonObject response = new JsonObject();
            JsonArray showArray = new JsonArray();
            for(ShowData s : server.getAll()) {
                JsonObject show = new JsonObject();
                show.add("id", gson.toJsonTree(s.getId()));
                show.add("artistName", gson.toJsonTree(s.getLocationName()));
                show.add("locationName", gson.toJsonTree(s.getArtistName()));
                show.add("startTime", gson.toJsonTree(s.getStartTime()));
                show.add("soldSeats", gson.toJsonTree(s.getSoldSeats()));
                show.add("availableSeats", gson.toJsonTree(s.getAvailableSeats()));

                showArray.add(show);
            }
            response.add("shows", showArray);
            return response;
        } catch (ServiceException e) {
            return getErrorResponse(e);
        }
    }

    private JsonObject getErrorResponse(ServiceException e) {
        JsonObject response = new JsonObject();
        response.addProperty("type", "error");
        response.addProperty("message", e.getMessage());
        return response;
    }

    private void sendResponse(JsonObject response) throws IOException{
        Gson builder = new GsonBuilder().create();
        response.addProperty("type", "response");
        output.write(builder.toJson(response) + '\n');
        output.flush();
    }

    private void sendUpdate(JsonObject response) throws IOException{
        Gson builder = new GsonBuilder().create();
        response.addProperty("type", "update");
        output.write(builder.toJson(response) + '\n');
        output.flush();
    }

    @Override
    public void changesOccured() {
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
        System.out.println("Notifying one client");

        JsonObject response = new JsonObject();
        response.add("type", gson.toJsonTree("update"));
        response.add("name", gson.toJsonTree("changesOccured"));

        try {
            sendUpdate(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
