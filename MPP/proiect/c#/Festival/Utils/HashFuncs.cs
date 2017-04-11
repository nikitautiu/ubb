using System.Security.Cryptography;
using System.Text;

namespace Festival.Utils
{
    public class HashFuncs
    {
        public static string CalculateMD5Hash(string input)
        {
            MD5 md5 = System.Security.Cryptography.MD5.Create();
            byte[] inputBytes = System.Text.Encoding.ASCII.GetBytes(input);
            byte[] hash = md5.ComputeHash(inputBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hash.Length; i++)
            {
                sb.Append(hash[i].ToString("x2")); // to lowercase hex
            }
            return sb.ToString();

        }
    }
}