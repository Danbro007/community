package lfie.danbro.community.community.advice;

import lfie.danbro.community.community.Exception.CustomizeExpection;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomizeExpectionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(Throwable ex, Model model) {
        if (ex instanceof CustomizeExpection) {
            model.addAttribute("message", ex.getMessage());
        } else {
            model.addAttribute("message", "服务器出错,请重新试试");
        }
        return new ModelAndView("error");
    }


}