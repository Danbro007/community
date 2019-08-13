package lfie.danbro.community.community.dto;


import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.Exception.CustomizeExpection;
import lombok.Data;

@Data
public class ResultDto {

    private String message;
    private Integer code;
    private Object data;

    public static ResultDto errorOf(Integer code,String message){
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeErrorCode code) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code.getCode());
        resultDto.setMessage(code.getMessage());
        return resultDto;
    }

    public static ResultDto successOf(CustomizeErrorCode code) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code.getCode());
        resultDto.setMessage(code.getMessage());
        return resultDto;
    }

    public static ResultDto successOf(CustomizeErrorCode code,Object data) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code.getCode());
        resultDto.setMessage(code.getMessage());
        resultDto.setData(data);
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeExpection e){
        return errorOf(e.getCode(),e.getMessage());
    }
}
