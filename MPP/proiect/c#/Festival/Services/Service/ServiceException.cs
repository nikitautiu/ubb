using System;

namespace Festival.Service
{
    public class ServiceException: Exception
    {
        public ServiceException(string message)
        : base(message)
        {
        }
    }
}