package lfie.danbro.community.community.Exception;

public enum  CustomizeErrorCode implements MyCustomizeErrorCode {
    QUESTION_NOT_FOUND("你要找的问题不存在,请换个试试!");
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
