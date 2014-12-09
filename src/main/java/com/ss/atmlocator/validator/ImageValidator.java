package com.ss.atmlocator.validator;

import com.ss.atmlocator.utils.Constants;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class ImageValidator implements Validator {

    private final int MAX_FILE_SIZE = 716800; //700kb
    private static final List<String> EXTENSIONS = Arrays.asList("jpg", "jpeg", "png","gif");

    @Autowired
    private MessageSource messages;

    @Override
    public boolean supports(Class<?> Clazz) {
        return MultipartFile.class.isAssignableFrom(Clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        MultipartFile image = (MultipartFile) object;
        if (null == image) return;
        if (image.getSize() > MAX_FILE_SIZE) {
            errors.rejectValue(Constants.USER_AVATAR,
                    messages.getMessage("file.size.limit", null, Locale.ENGLISH));
            return;
        } else if (!isValidExtension(image)) {
            errors.rejectValue(Constants.USER_AVATAR,
                    messages.getMessage("file.extension", null, Locale.ENGLISH));
        }
    }

    private boolean isValidExtension(MultipartFile image) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        return EXTENSIONS.contains(extension);
    }
}
