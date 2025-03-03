package life.fuzhong.community.exception;

public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(String message) {
        this.message = message;
    }

    public CustomizeException(CustomizeErrorCode customizeErrorCode) {
        this.message = customizeErrorCode.getMessage();
        this.code = customizeErrorCode.getCode();
    }


    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode(){ return code;}


}
