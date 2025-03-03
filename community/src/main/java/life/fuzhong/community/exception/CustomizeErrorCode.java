package life.fuzhong.community.exception;

public enum CustomizeErrorCode implements ICustomizeError{
    QUESTION_NOT_FOUND(2001,"你的问题找不到了，要不换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"用户未登录"),
    SYS_ERROR(2004, "服务器出现故障了，正在尽力维修中，请稍后再试"),
    TYPE_PARAM_WRONG(2005, "评论类型有误，请重新选择"),
    COMMENT_NOT_FOUND(2006, "评论以被删除，无法评论");


    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

}
