package com.ss.atmlocator.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by roman on 27.11.14.
 */
public class UploadFileUtils {
    private final static Logger log = Logger.getLogger(UploadFileUtils.class);
    public static final String RESOURCES_FOLDER = "\\resources\\images\\";


    /**
     *  Save user avatar
     */
    public static void save(MultipartFile image,String filename, HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/") + RESOURCES_FOLDER;
        File file = new File(path + filename);
        FileUtils.writeByteArrayToFile(file, image.getBytes());
    }

    /**
     *  Save bank logo or icon image with new filename based on bank_id
     *  to avoid possibility of the same files for different banks
     */
    public static String saveBankImage(MultipartFile image, String prefix, int bank_id, HttpServletRequest request){
        String newname = null;
        try{
            if (image != null && !image.isEmpty()) {
                newname =  prefix + bank_id +"."+FilenameUtils.getExtension(image.getOriginalFilename());
                save(image, newname, request);
            }
        } catch (IOException e) {
            log.error("IO error saving image "+prefix+ bank_id);
            e.printStackTrace();
        }
        return newname;
    }




}
