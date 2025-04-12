package New1.Ex3;

class LongLineException extends RuntimeException {          /// /класс исключение для строки больше 1024 символов
    public LongLineException(String message) {
        super(message);
    }
}
