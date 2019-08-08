package lfie.danbro.community.community.Exception;

public class CustomizeExpection extends RuntimeException {

    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }


    public CustomizeExpection(MyCustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

}

