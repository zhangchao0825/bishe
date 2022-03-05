package com.vote.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

@Component
@PropertySource({"classpath:files.properties"})
public class FilesUtil {


    /**
     * 获取扩展名
     *
     * @param file
     * @return
     */
    @Value("${file.absolutePath}")
    private String absolutePath;

    @Value("${file.relativePath}")
    private String relativePath;

    public String getRelativePath() {
        return relativePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }



    /**
     * 获取文件的扩展名
     *
     * @param file
     * @return
     * @throws Exception
     */
    public String getFileType(File file) throws Exception {
        String fileName = file.getName();
        String expandedName = "";
        expandedName = getFileExpandedName(fileName);
        if (expandedName.equals("jpeg") || expandedName.equals("png") || expandedName.equals("jpg")) {
            return "img";
        } else if (expandedName.equals("pdf") || expandedName.equals("xlsx") || expandedName.equals("xls") || expandedName.equals("doc") || expandedName.equals("docx") || expandedName.equals("ppt") || expandedName.equals("pptx") || expandedName.equals("txt")) {
            return "text";
        } else {
            return "other";
        }
    }

    /**
     * 获取文件的扩展名
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public String getFileExpandedName(String fileName) throws Exception {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(Locale.ROOT);
        } else {
            throw new MyException("文件扩展名异常");
        }
    }

    /**
     * 随机生成文件名
     *
     * @param fileName
     * @return
     */
    public String getRandomFileName(String fileName) {
        String expandedName = "";
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            expandedName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(Locale.ROOT);
        }
        return UUID.randomUUID().toString().replace("-", "") + "." + expandedName;
    }



    /**
     * 转化为相对路径
     */
    public String toRelativePath(String absolutePath) {
        return absolutePath.replace(this.getAbsolutePath(), this.relativePath);
    }


    /**
     * 自定义的文件异常类
     */
    public class MyException extends Exception {
        public String message;

        public MyException(String message) {
            this.message = message;
        }

        // Overrides Exception's getMessage()
        @Override
        public String getMessage() {
            return message;
        }
    }

}
