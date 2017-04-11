using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Model.Validator
{
    delegate void Validator<T>(T entity);

    public static class Validators
    {
        public static void ValidatePurhcase(Purchase entity)
        {
            string ErrorString = "";
            if (entity.Id < 0)
                ErrorString += "Id must be positive.";
            if (entity.Quantity < 0)
                ErrorString += "Quantity must be positive.";
            if (entity.ClientName == "")
                ErrorString += "Name must be non-empty.";
            if (ErrorString != "")
                throw new ValidationException(ErrorString);
        }
    }
}
