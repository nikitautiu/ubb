package Domain.Validator;


import Domain.Client;

public class ClientValidator implements IValidator<Client> {
    @Override
    public void accept(Client client) throws ValidationException {
        String errorString = "";

        if( client.getId() < 0 )
            errorString += "ID-ul trebuie sa fie pozitiv. ";

        if( client.getName() == "" )
            errorString += "Numele nu poate fi vid. ";

        if( client.getName().length() == 1 )
            errorString += "Numele trebuie să aiba minim 2 caractere. ";

        if(!errorString.equals(""))
            throw new ValidationException(errorString);
    }
}
