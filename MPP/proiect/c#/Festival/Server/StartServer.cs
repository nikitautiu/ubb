using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Festival.Repository;
using Festival.Service;
using Festival.Networking;
using Festival.Server;

namespace Festival.Server
{
    class ServerProgram
    {
        static void Main(string[] args)
        {
            var showRepo = new ShowDataSqlRepo("festival.db");
            var purchaseRepo = new PurchaseSqlRepo("festival.db");
            var userRepo = new UserSqlRepo("festival.db");
            var service = new Server.Server(showRepo, purchaseRepo, userRepo);

            SerialServer server = new SerialServer("127.0.0.1", 8080, service);
            server.Start();
            Console.WriteLine("Server started ...");
            //Console.WriteLine("Press <enter> to exit...");
            Console.ReadLine();
        }
    }
    public class SerialServer : ConcurrentServer
    {
        private IServerService server;
        private JsonClientWorker worker;
        public SerialServer(string host, int port, IServerService server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialChatServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new JsonClientWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}
