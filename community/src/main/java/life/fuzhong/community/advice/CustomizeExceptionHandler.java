package life.fuzhong.community.advice;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import life.fuzhong.community.dto.ResultDTO;
import life.fuzhong.community.exception.CustomizeErrorCode;
import life.fuzhong.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)

    ModelAndView handle(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {

            ResultDTO resultDTO;

            if (ex instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            } else {
               resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;

        }else {
            if( ex instanceof CustomizeException){
                model.addAttribute("message", ex.getMessage());
            }else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }

}
