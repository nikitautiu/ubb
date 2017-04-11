namespace Festival.Networking
{
    public class DTO
    {
        public string type { get; set; }
        public string name { get; set; }
    }

    public class ErrorDTO : DTO
    {
        public string message { get; set; }

        public ErrorDTO(string message)
        {
            type = "error";
            name = "error";
            this.message = message;
        }
    }
}