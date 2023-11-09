package by.bsuir.retail.service.exception;

public class NotSufficientMaterialAmountException extends RuntimeException {
    public NotSufficientMaterialAmountException(int leftover, int neededQuantity, String materialName) {
        super("leftover for material " + materialName +
                " is " + leftover +
                ", which is lower than needed amount " + neededQuantity);
    }
}
