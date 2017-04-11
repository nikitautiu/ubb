using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Networking
{
    public class LoginRequestDTO: DTO
    {
        
        public string username { get; set; }
        public string password { get; set; }

        public LoginRequestDTO(string username, string password)
        {
            type = "request";
            name = "login";
            this.username = username;
            this.password = password;
        }
    }

    public class LoginResponseDTO : DTO
    {
        public bool success { get; set; }

        public LoginResponseDTO(bool success)
        {
            type = "response";
            name = "login";
            this.success = success;
        }
    }
}
