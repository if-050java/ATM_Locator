package com.ss.atmlocator.utils;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by student on 12/10/2014.
 */
public class UploadedFile {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
