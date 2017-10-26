package com.goodsoft.society_zy.config.filter;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * version v1.0
 * function 资源不存在跳转
 * Created by 严彬荣 on 2017/8/24.
 */
/*@Controller*/
public class SystemError implements ErrorController {
    private static final String ERRORPAHT = "/error";

    @RequestMapping(value = ERRORPAHT)
    public String handleError() {
        return "redirect:/home.html";
    }

    @Override
    public String getErrorPath() {
        return ERRORPAHT;
    }

}
