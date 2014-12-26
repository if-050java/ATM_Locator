package com.ss.atmlocator.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

import static com.ss.atmlocator.utils.Constants.RESOURCES_FOLDER;

/**
 * Created by student on 12/10/2014.
 */
public class UploadedFile {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UploadedFile.class);

    private MultipartFile file;

    /**
     * Save image with a new filename based on subject id
     */
    @NotNull
    public static String saveImage(MultipartFile image, String prefix, int id, HttpServletRequest request) throws IOException {
        String newName = null;
        if (image != null && !image.isEmpty()) {
            newName = prefix + id + "." + FilenameUtils.getExtension(image.getOriginalFilename());
            logger.info("Trying to save image: " + newName);
            try {
                String path = request.getSession().getServletContext().getRealPath("/") + RESOURCES_FOLDER;
                File file = new File(path + newName);
                FileUtils.writeByteArrayToFile(file, image.getBytes());
            } catch (IOException ioe) {
                logger.error(ioe.getMessage(), ioe);
                throw ioe;
            }
        }
        return newName;
    }

    public static void deleteResourceFile(@NotNull String name, HttpServletRequest request) {
        if (name != null) {
            String path = request.getSession().getServletContext().getRealPath("/") + RESOURCES_FOLDER;
            File file = new File(path + name);
            try {
                file.delete();
            } catch (SecurityException se) {
                logger.error(se.getMessage(), se);
            }
        }
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
