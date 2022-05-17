package com.xiamu.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {

    private static final List<String> content_types= Arrays.asList("image/gif","image/jpeg","image/jpg","image/png");

    private static final Logger LOGGER= LoggerFactory.getLogger(UploadService.class);
    @Autowired
    private FastFileStorageClient storageClient;
    public String uploadImage(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        //StringUtils.substringAfterLast(originalFilename,".") 不去使用此方法
        //校验文件类型
        String contentType = file.getContentType();
        if(!content_types.contains(contentType)){
            LOGGER.info("文件类型不合法：{}",originalFilename);
            return null;
        }
        try {
            //校验文件内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage==null){
                LOGGER.info("文件内容不合法：{}",originalFilename);
                return null;
            }
//        保存到文件服务器
 //           file.transferTo(new File("D:\\hm49\\image"+originalFilename));
            String ext = StringUtils.substringAfterLast(originalFilename, ".");
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);

//        返回url进行回显
            //return "http://image.leyou.com/"+originalFilename;
            return "http://image.xiamu.com/"+storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.info("服务器内部错误："+originalFilename);
            e.printStackTrace();
        }
        return null;
    }
}
