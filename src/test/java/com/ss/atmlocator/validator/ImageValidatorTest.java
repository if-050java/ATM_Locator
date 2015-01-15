package com.ss.atmlocator.validator;

import com.ss.atmlocator.utils.UploadedFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roman on 05.01.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/test-configuration.xml")
public class ImageValidatorTest {
    @Autowired
    ImageValidator imageValidator;
    UploadedFile validFile;
    UploadedFile invalidExtention;
    UploadedFile invalidSize;
    @Autowired
    private MessageSource messages;

    @Before
    public void setup() {
        //valid image mock
        MultipartFile validFileMultipartFile = mock(MultipartFile.class);
        when(validFileMultipartFile.getOriginalFilename()).thenReturn("my-image.jpg");
        validFile = new UploadedFile(validFileMultipartFile);

        //invalid image extention mock
        MultipartFile invalidExtentionMultipartFile = mock(MultipartFile.class);
        when(invalidExtentionMultipartFile.getOriginalFilename()).thenReturn("my-image.doc");
        invalidExtention = new UploadedFile(invalidExtentionMultipartFile);

        //invalid image size mock
        MultipartFile invalidSizeMultipartFile = mock(MultipartFile.class);
        when(invalidSizeMultipartFile.getSize()).thenReturn((long) (1 * 1024 * 1024));//1 mb
        when(invalidSizeMultipartFile.getOriginalFilename()).thenReturn("my-image.jpg");
        invalidSize = new UploadedFile(invalidSizeMultipartFile);
    }

    @Test
    public void testValidImage() {
        Errors errors = new BeanPropertyBindingResult(validFile, "image");
        imageValidator.validate(validFile, errors);
        assertFalse(errors.hasErrors());
    }
    @Test
    public void testInvalidImageExtension() {
        Errors errors = new BeanPropertyBindingResult(invalidExtention, "image");
        imageValidator.validate(invalidExtention, errors);
        assertTrue(errors.hasErrors());
        assertEquals(errors.getAllErrors().get(0).getCode(), messages.getMessage("file.extension", null, Locale.ENGLISH));
    }
    @Test
    public void testInvalidImageSize() {
        Errors errors = new BeanPropertyBindingResult(invalidSize, "image");
        imageValidator.validate(invalidSize, errors);
        assertTrue(errors.hasErrors());
        assertEquals(errors.getAllErrors().get(0).getCode(), messages.getMessage("file.size.limit", null, Locale.ENGLISH));
    }
}