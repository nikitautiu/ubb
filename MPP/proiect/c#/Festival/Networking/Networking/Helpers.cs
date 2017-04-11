using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace Festival.Networking
{
    public static class Helpers
    {
        public static DTO DeserializeDTO(string line)
        {
            // decide what object to tdeserialize it into
            var dict =  JsonConvert.DeserializeObject<Dictionary<string, dynamic>>(line);
            if (((string) dict["type"]) == "error")
                return JsonConvert.DeserializeObject<ErrorDTO>(line);
            if (((string) dict["type"]) == "update")
            {
                if(((string)dict["name"]) == "changesOccurred")
                    return JsonConvert.DeserializeObject<ChangesOccuredDTO>(line,
                        new IsoDateTimeConverter { DateTimeFormat = "dd/MM/yyyy HH:mm" });
            }
            if (((string)dict["type"]) == "request")
            {
                if (((string)dict["name"]) == "login")
                    return JsonConvert.DeserializeObject<LoginRequestDTO>(line);
                if (((string)dict["name"]) == "getAll")
                    return JsonConvert.DeserializeObject<GetAllRequestDTO>(line,
                        new IsoDateTimeConverter { DateTimeFormat = "dd/MM/yyyy HH:mm" });
                if (((string)dict["name"]) == "addPurchase")
                    return JsonConvert.DeserializeObject<AddPurchaseRequestDTO>(line);
            }
            if (((string)dict["type"]) == "response")
            {
                if (((string)dict["name"]) == "login")
                    return JsonConvert.DeserializeObject<LoginResponseDTO>(line);
                if (((string)dict["name"]) == "getAll")
                    return JsonConvert.DeserializeObject<GetAllResponseDTO>(line,
                        new IsoDateTimeConverter { DateTimeFormat = "dd/MM/yyyy HH:mm" });
                if (((string)dict["name"]) == "addPurchase")
                    return JsonConvert.DeserializeObject<AddPurchaseResponseDTO>(line);
            }
            return null;
        }
    }
}