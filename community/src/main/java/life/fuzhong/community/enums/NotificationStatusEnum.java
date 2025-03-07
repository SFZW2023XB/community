package life.fuzhong.community.enums;

public enum NotificationStatusEnum {
    READ(1),
    UNREAD(0),
    ;
    private Integer status;

    NotificationStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
