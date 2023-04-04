package kissloryshy.xxicentury.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("Товар с номером " + productId + " не найден");
    }
}
