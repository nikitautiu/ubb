using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading.Tasks;
using Festival.Model;
using Festival.Service;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace Festival.Networking
{
    public class JsonClientWorker : IClientService
    {
        private IServerService server;
        private TcpClient connection;

        private NetworkStream stream;
        private StreamWriter outStream;
        private StreamReader inStream;

        private volatile bool connected;
        public JsonClientWorker(IServerService server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                inStream = new StreamReader(stream);
                outStream = new StreamWriter(stream);
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    var line = inStream.ReadLine();
                    var request = Helpers.DeserializeDTO(line);
                    Console.WriteLine("response received " + line);
                    var response = this.handleRequest(request);
                    this.sendResponse(response);
                }
                catch (ArgumentNullException e)
                {
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }

        private void sendResponse(DTO response)
        {
            try
            {
                var requestStr = JsonConvert.SerializeObject(response,
                    new IsoDateTimeConverter { DateTimeFormat = "dd/MM/yyyy HH:mm" });
                outStream.WriteLine(requestStr);
                outStream.Flush();
            }
            catch (Exception e)
            {
                throw new ServiceException("Error sending object " + e);
            }
        }

        private DTO handleRequest(DTO request)
        {
            if (request.name == "login")
            {
                Console.WriteLine("Login request ...");

                var loginRequest = (LoginRequestDTO) request;
                try
                {
                    lock (this.server)
                    {
                        var success = this.server.Login(loginRequest.username, loginRequest.password, this);
                        return new LoginResponseDTO(success);
                    }
                }
                catch (ServiceException e)
                {
                    connected = false;
                    return new ErrorDTO(e.Message);
                }
            }
            if (request.name == "getAll")
            {
                Console.WriteLine("GetAll request ...");

                var getAllRequest = (GetAllRequestDTO) request;
                try
                {
                    lock (this.server)
                    {
                        return new GetAllResponseDTO(this.server.GetAll());
                    }
                }
                catch (ServiceException e)
                {
                    return new ErrorDTO(e.Message);
                }
            }
            if (request.name == "addPurchase")
            {
                Console.WriteLine("AddPurchase request ...");
                var addPurchaseRequest = (AddPurchaseRequestDTO)request;
                try
                {
                    lock (this.server)
                    {
                        this.server.AddPurchase(addPurchaseRequest.showId, addPurchaseRequest.clientName,
                            addPurchaseRequest.quantity);
                    }
                }
                catch (ServiceException e)
                {
                    return new ErrorDTO(e.Message);
                }
                return new AddPurchaseResponseDTO();
            }
            return null;
        }

        public void changesOccured(IEnumerable<ShowData> changed)
        {
            Console.WriteLine("Notifying one client of changes ...");
            try
            {
                sendResponse(new ChangesOccuredDTO(changed));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);

            }
        }
    }
}
