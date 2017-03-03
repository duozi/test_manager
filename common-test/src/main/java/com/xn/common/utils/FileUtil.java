package com.xn.common.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by xn056839 on 2016/8/30.
 */
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    //检查文件夹是否存在
    public static boolean makeDirs(String folderName) {
        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    public static void fileWrite(String fileName, String contents) {
        checkNotNull(fileName, "Provided file name for writing must NOT be null.");
        checkNotNull(contents, "Unable to write null contents.");
        int i = fileName.lastIndexOf(File.separator);
        String folder = fileName.substring(0, i);
        makeDirs(folder);
        final File newFile = new File(fileName);
        try {
            Files.write(contents.getBytes("utf-8"), newFile);
        } catch (IOException fileIoEx) {
            logger.error("ERROR trying to write to file '" + fileName + "' - "
                    + fileIoEx.toString());
        }
    }

    public static List fileReadeForList(File file) {

        List<String> lines = null;

        try {
            lines = Files.readLines(file, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static String fileReadeForStr(File file) {

        List<String> lines = null;
        String result = "";
        try {
            lines = Files.readLines(file, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            result += "\t\t" + line + "\n";
        }
        return result;
    }

    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }

    /***
     * 保存文件
     *
     * @param file
     * @param filePath
     * @return
     */
    public static boolean saveFile(HttpServletRequest request, MultipartFile file, String filePath) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 保存的文件路径(如果用的是Tomcat服务器，文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中  )
                File saveDir = new File(filePath);
                if (!saveDir.getParentFile().exists())
                    saveDir.getParentFile().mkdirs();

                // 转存文件
                file.transferTo(saveDir);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    
    public static void main(String[] args) {
        String filePath = PropertyUtil.getProperty("upload_path") + "dddd.jar";
        System.out.print(filePath);
	}
    

}
