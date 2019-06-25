package com.beemall.shop.controller;

import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：bee
 * @date ：Created in 2019/6/25 15:42
 * @description：
 * @modified By：
 */
@RestController
public class UploadController {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Value("${file.server.url}")
    private String fileServerUrl;

    @PostMapping("/upload")
    public ResponseData upload(MultipartFile file) {
        //1、取文件的扩展名
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        FileInputStream inputStream = null;
        String path = "";
        try {
            //2.执行上传
            inputStream = (FileInputStream) file.getInputStream();
            StorePath storePath = storageClient.uploadFile(inputStream, file.getSize(),extName,null );
            //3.获取路径
            path = storePath.getFullPath();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Map<String, String> result = new HashMap<>();
        result.put("url",fileServerUrl + path)  ;
        return ResponseDataUtil.buildSuccess(result);
    }
}
