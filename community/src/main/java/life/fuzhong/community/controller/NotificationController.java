package life.fuzhong.community.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import life.fuzhong.community.dto.NotificationDTO;
import life.fuzhong.community.enums.NotificationEnum;
import life.fuzhong.community.model.Users;
import life.fuzhong.community.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotificationController {

    @Resource
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id,
                          HttpServletRequest request){

        Users users =(Users) request.getSession().getAttribute("users");

        if(users == null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, users);
        if (notificationDTO == null) {
            return "redirect:/";
        }

        if(NotificationEnum.REPLY_QUESTOIN.getType() == notificationDTO.getType()
            || NotificationEnum.REPLY_COMMMENT.getType() == notificationDTO.getType()){
            return "redirect:/question/" + notificationDTO.getOuterid();
        }else{
            return "redirect:/";
        }






    }
}
