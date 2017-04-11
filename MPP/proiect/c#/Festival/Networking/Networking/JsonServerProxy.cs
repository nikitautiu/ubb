using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Threading;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using Festival.Model;
using Festival.Service;
using Festival.Networking;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;


namespace Festival.Networking
{
    public class JsonServerProxy : IServerService
    {
        private string host;
        private int port;

        private IClientService client;

        private NetworkStream stream;
        private StreamWriter outStream;
        private StreamReader inStream;

        private IFormatter formatter;
        private TcpClient connection;

        private Queue<DTO> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;
        public JsonServerProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<DTO>();
        }

        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                //output.close();
                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }

        }

        private void sendRequest(object request)
        {
            try
            {
                var requestStr = JsonConvert.SerializeObject(request,
                    new IsoDateTimeConverter { DateTimeFormat = "dd/MM/yyyy HH:mm" });
                outStream.WriteLine(requestStr);
                outStream.Flush();
            }
            catch (Exception e)
            {
                throw new ServiceException("Error sending object " + e);
            }

        }

        private DTO readResponse()
        {
            DTO response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    //Monitor.Wait(responses); 
                    response = responses.Dequeue();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return response;
        }

        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                inStream = new StreamReader(stream);
                outStream = new StreamWriter(stream);
                formatter = new BinaryFormatter();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }


        private void handleUpdate(DTO updateResponse)
        {
            if (updateResponse.name == "changesOccurred")
                this.changesOccured((ChangesOccuredDTO) updateResponse);
        }

        private void changesOccured(ChangesOccuredDTO updateResponse)
        {
            this.client.changesOccured(updateResponse.modelShow);
        }

        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    var line = inStream.ReadLine();
                    var response = Helpers.DeserializeDTO(line);
                    Console.WriteLine("response received " + line);
                    if (response.type == "update")
                    {
                        handleUpdate(response);
                    }
                    else
                    {
                        lock (responses)
                        {
                            responses.Enqueue(response);
                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }

            }
        }

        public void AddPurchase(int showId, string clientName, int quantity)
        {
            sendRequest(new AddPurchaseRequestDTO(showId, clientName, quantity));
            var response = readResponse();

            if (response.type == "error")
            {
                throw new ServiceException(((ErrorDTO)response).message);
            }

            var getAllResponse = (AddPurchaseResponseDTO)response;
        }

        public bool Login(string username, string password, IClientService cli)
        {
            initializeConnection();
            sendRequest(new LoginRequestDTO(username, password));
            object response = readResponse();

            if (((DTO)response).type == "error")
            {
                closeConnection();
                throw new ServiceException(((ErrorDTO)response).message);
            }

            LoginResponseDTO loginResponse = (LoginResponseDTO)response;
            if (loginResponse.success)
            {
                this.client = cli;
                return true;
            }
            closeConnection();
            return false;

        }

        public IEnumerable<ShowData> GetAll()
        {
            sendRequest(new GetAllRequestDTO());
            var response = readResponse();

            if (response.type == "error")
            {
                throw new ServiceException(((ErrorDTO)response).message);
            }

            var getAllResponse = (GetAllResponseDTO)response;
            return getAllResponse.modelShow;
        }

        
    }

}