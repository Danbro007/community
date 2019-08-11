package lfie.danbro.community.community.advice;

import com.alibaba.fastjson.JSON;
import lfie.danbro.community.community.dto.ResultDto;
import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.Exception.CustomizeExpection;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExpectionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request,
                                           HttpServletResponse response,
                                           Throwable e, Model model) {
        String contentType = request.getContentType();
        if (contentType != null) {
            if (contentType.equals("application/json")) {
                ResultDto resultDto;
                if (e instanceof CustomizeExpection) {
                    resultDto = ResultDto.errorOf((CustomizeExpection) e);
                } else {
                    resultDto = ResultDto.errorOf(CustomizeErrorCode.SERVER_ERROR);
                }
                try {
                    response.setContentType("application/json");
                    response.setStatus(200);
                    response.setCharacterEncoding("utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.write(JSON.toJSONString(resultDto));
                    writer.close();
                } catch (IOException e1) {
                }
                return null;
            } else {
                if (e instanceof CustomizeExpection) {
                    model.addAttribute("message", e.getMessage());
                } else {
                    model.addAttribute("message", "服务器出错,请重新试试");
                }
                return new ModelAndView("error");
            }
        }else {
            return null;
        }

    }


}