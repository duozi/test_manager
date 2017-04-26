package com.xn.manage.utils;

import com.xn.common.utils.FileUtil;
import com.xn.common.utils.PropertyUtil;
import com.xn.manage.Enum.CommonResultEnum;
import com.xn.common.base.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xn058121 on 2017/3/2.
 */
public class FileUpload {
    private static final Logger logger = LoggerFactory.getLogger(FileUpload.class);

    private static final String file_separator = "/";

    public static CommonResult upload(MultipartFile[] files, String folderName, HttpServletRequest request){
        CommonResult result = new CommonResult();
        result.setMessage("上传成功！");
        String jarPath = "";
        try{
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    Long maxSize = Long.parseLong(PropertyUtil.getProperty("maxFileSize"));
                    if(file.getSize()>maxSize){
                        result.setCode(CommonResultEnum.ERROR.getReturnCode());
                        result.setMessage("文件大小不能超过"+ maxSize +"KB");
                        return result;
                    }
                    // 保存文件
                    jarPath = PropertyUtil.getProperty("upload_path") + file_separator + folderName + file_separator + file.getOriginalFilename();
                    FileUtil.saveFile(request, file,jarPath);
                    result.setData(jarPath);
                }
            }
        }catch (Exception e){
            int code = CommonResultEnum.ERROR.getReturnCode();
            String message = e.getMessage();
            result.setCode(code);
            result.setMessage(message);
            logger.error("上传操作异常｛｝",e);
        }
        return result;
    }

}
