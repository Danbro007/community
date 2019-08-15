package lfie.danbro.community.community.controller;


import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.Exception.CustomizeExpection;
import lfie.danbro.community.community.dto.FileDto;
import lfie.danbro.community.community.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@ResponseBody
public class FileController {

    @Autowired
    UCloudProvider uCloudProvider;

    /**
     * 把接收到的请求转换成multipart请求,获取到要上传文件的文件名,文件类型等信息,然后把这些相关信息发送到UCloud SDK,上传到UCloud云存储
     * @param request 接收的的请求
     * @return
     */
    @PostMapping("/file/upload")
    public FileDto fileUpload(HttpServletRequest request){
        FileDto fileDto = new FileDto();
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        try {
            String fileUrl = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            fileDto.setUrl(fileUrl);
            fileDto.setSuccess(1);
            return fileDto;
        } catch (IOException e) {
            throw new CustomizeExpection(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
    }
}
