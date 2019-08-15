package lfie.danbro.community.community.Exception;

public enum CustomizeErrorCode implements MyCustomizeErrorCode {
    QUESTION_NOT_FOUND(4001, "你要找的问题不存在,请换个试试!"),
    COMMENT_NOT_FOUND(4002, "你要回复的评论或者问题不存在!"),
    NO_LOGIN(4003, "请先登录!"),
    SUCCESS(2000, "成功!"),
    SERVER_ERROR(5000, "服务器出错!"),
    COMMENT_PARAM_ERROR(4004,"评论类型错误!"),
    NOTIFICATION_NOT_FOUND(4005,"通知不存在!"),
    FILE_UPLOAD_FAIL(4006,"文件上传失败,请重试.");
    private String message;
    private Integer code;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
