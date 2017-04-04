package networking.utils;


import networking.protocol.ClientJsonWorker;
import services.IServerService;

import java.net.Socket;


public class JsonConcurrentServer extends networking.utils.AbsConcurrentServer {
    private IServerService serverService;
    public JsonConcurrentServer(int port, IServerService serverService) {
        super(port);
        this.serverService = serverService;
        System.out.println("Chat- JsonConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientJsonWorker worker=new ClientJsonWorker(serverService, client);
        Thread tw=new Thread(worker);
        return tw;
    }


}
