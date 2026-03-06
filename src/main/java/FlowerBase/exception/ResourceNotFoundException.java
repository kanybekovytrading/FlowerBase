package FlowerBase.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException bouquet(Long id) {
        return new ResourceNotFoundException("Букет с id=" + id + " не найден");
    }

    public static ResourceNotFoundException category(Long id) {
        return new ResourceNotFoundException("Категория с id=" + id + " не найдена");
    }
}
