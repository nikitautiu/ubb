package model.validator;

import model.Purchase;

/**
 * Created by archie on 12/22/2016.
 */
public class PurchaseValidator implements IValidator<Purchase> {
    @Override
    public void accept(Purchase purchase) {
        String errorString = "";
        if (purchase.getId() < 0)
            errorString += "Id must be positive.";
        if (purchase.getQuantity() < 0)
            errorString += "Quantity must be positive.";
        if (purchase.getClientName().equals(""))
            errorString += "Name must be non-empty.";
        if(!errorString.equals(""))
            throw new ValidationException(errorString);
    }
}
