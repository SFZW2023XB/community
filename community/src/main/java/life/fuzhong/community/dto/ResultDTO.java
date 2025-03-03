package life.fuzhong.community.dto;

import life.fuzhong.community.exception.CustomizeErrorCode;
import life.fuzhong.community.exception.CustomizeException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

@Data
public class ResultDTO {
    private String message;
    private Integer code;
    public static ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode customizeErrorCode) {

        return errorOf(customizeErrorCode.getCode(), customizeErrorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(), ex.getMessage());
    }

    public static ResultDTO successOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage("请求成功");
        resultDTO.setCode(200);
        return resultDTO;
    }


}
