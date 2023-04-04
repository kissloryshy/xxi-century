package kissloryshy.xxicentury.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super("Заказ с номером " + orderId + " не найден.");
    }
}
