package lfie.danbro.community.community.Enum;

public enum NotificationStatusEnum {
    UNREAD(1),
    READ(0);

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    NotificationStatusEnum(Integer status) {
        this.status = status;
    }
}
