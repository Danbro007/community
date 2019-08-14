package lfie.danbro.community.community.Enum;

public enum NotificationTypeEnum {
    Reply_Question(1,"回复了问题"),
    Reply_Comment(2,"回复了评论");

    private Integer type;
    private String message;

    public Integer getType() {
        return type;
    }
    public String getMessage() {
        return message;
    }

    NotificationTypeEnum(Integer type, String message) {
        this.type = type;
        this.message = message;
    }
}
