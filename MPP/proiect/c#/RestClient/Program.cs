using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Net.Http.Formatting;

namespace RestClient
{
    class MainClass
    {
        static HttpClient client = new HttpClient();

        public static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            RunAsync().Wait();
        }


        static async Task RunAsync()
        {
            client.DefaultRequestHeaders.Accept.Clear();
            //client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("text/plain"));
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            // Get the string
            //String text = await GetTextAsync("http://localhost:8080/chat/greeting");
            //Console.WriteLine("am obtinut {0}", text);
            //Get one user
            List<Artist> result = await GetArtistAsync("http://localhost:8080/artists/");
            foreach(var artist in result)
                Console.WriteLine(artist);
            Console.ReadLine();
        }

        static async Task<List<Artist>> GetArtistAsync(string path)
        {
            List<Artist> product = null;
            HttpResponseMessage response = await client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                product = await response.Content.ReadAsAsync<List<Artist>>();
            }
            return product;
        }

    }
    public class Artist
    {
        public string id { get; set; }
        public string name { get; set; }

        public override string ToString()
        {
            return string.Format("[Artist: Name={0}, Id={1}]", name, id);
        }
    }

}
