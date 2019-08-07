package lfie.danbro.community.community.Exception;

public class CustomizeExpection extends RuntimeException {

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public CustomizeExpection(MyCustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    public CustomizeExpection(String message) {
        this.message = message;
    }
}
