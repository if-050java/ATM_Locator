package com.ss.atmlocator.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by roman on 27.11.14.
 */
public class UploadFileUtils {
    public static final String RESOURCES_FOLDER = "\\resources\\images\\";

    public static void save(MultipartFile image,String filename, HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/") + RESOURCES_FOLDER;
        File file = new File(path + filename);
        FileUtils.writeByteArrayToFile(file, image.getBytes());
    }
}
