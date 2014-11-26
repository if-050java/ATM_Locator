package com.ss.atmlocator.validator;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageValidator implements Validator {

    private final int MAX_FILE_SIZE = 716800; //700kb

    @Override
    public boolean supports(Class<?> Clazz) {
        return MultipartFile.class.isAssignableFrom(Clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        MultipartFile image = (MultipartFile) object;
        if (null == image) return;
        if (image.getSize() > MAX_FILE_SIZE) {
            errors.rejectValue("avatar", ValidationResult.LIMIT_FILE_SIZE);
            return;
        }
        if (!FilenameUtils.getExtension(image.getOriginalFilename()).equals("jpg") || !FilenameUtils.getExtension(image.getOriginalFilename()).equals("png")) {
            errors.rejectValue("avatar", ValidationResult.INVALID_FILE_EXTENSION);
            return;
        }
    }
}
