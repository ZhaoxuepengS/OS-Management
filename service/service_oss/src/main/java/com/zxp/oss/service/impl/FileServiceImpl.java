package com.zxp.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zxp.oss.service.FileService;
import com.zxp.oss.util.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    public String fileUpload(MultipartFile multipartFile){
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String  bucket = ConstantPropertiesUtils.BUCKET_NAME;

        String uploadUrl=null;

        try{

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            String fileName = multipartFile.getOriginalFilename();
            System.out.println("filename="+fileName);
            InputStream inputStream = multipartFile.getInputStream();
            System.out.println("inputStream="+inputStream);


            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;

            String datePath = new DateTime().toString("yyyy/MM/dd");

            fileName = datePath+"/"+fileName;

            // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
            ossClient.putObject(bucket, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            uploadUrl = "https://" + bucket +"."+ endpoint + "/" + fileName;
            return  uploadUrl;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
}
