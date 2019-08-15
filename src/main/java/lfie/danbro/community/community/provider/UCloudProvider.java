package lfie.danbro.community.community.provider;


import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import lfie.danbro.community.community.Exception.CustomizeErrorCode;
import lfie.danbro.community.community.Exception.CustomizeExpection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;


@Service
public class UCloudProvider {

    @Value("${ucloud.ufile.private-key}")
    private String privateKey;

    @Value("${ucloud.ufile.public-key}")
    private String publicKey;
    @Value("${ucloud.ufile.bucketName}")
    private String bucketName;

    /**
     * 上传文件到UCloud云存储
     *
     * @param fileStream 文件输入流
     * @param mimeType   文件类型
     * @param fileName   文件名
     * @return
     */
    public String upload(InputStream fileStream, String mimeType, String fileName) {
        String[] split = fileName.split("\\.");
        String generatedFileName;
        if (split.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + split[split.length - 1];
        } else {
            throw new CustomizeExpection(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        try {
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(
                    publicKey, privateKey);
            ObjectConfig config = new ObjectConfig("cn-sh2", "ufileos.com");

            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generatedFileName)
                    .toBucket(bucketName)
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })
                    .execute();
            if (response != null && response.getRetCode() == 0) {
                String url = UfileClient.object(objectAuthorization, config)
                        .getDownloadUrlFromPrivateBucket(generatedFileName, bucketName, 24 * 60 * 60)
                        .createUrl();
                return url;
            }else{
                throw new CustomizeExpection(CustomizeErrorCode.FILE_UPLOAD_FAIL);
            }
        } catch (UfileClientException e) {
            throw new CustomizeExpection(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (UfileServerException e) {
            throw new CustomizeExpection(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
    }


}
