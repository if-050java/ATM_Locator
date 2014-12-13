package com.ss.atmlocator.utils;

import org.apache.commons.io.FilenameUtils;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Created by Olavin on 13.12.2014.
 */
public class ImageUtil {

    public static void createLowContrastGrayscaleImageCopy(String sourceFileName) throws IOException {
        BufferedImage source = null;
        BufferedImage destination = null;

        File sourceFile = new File(sourceFileName);
        String format = getImageFormat(sourceFile);

        // Create destination file name from source by adding '_gray' suffix
        String destFileName = FilenameUtils.removeExtension(sourceFileName)
                + "_gray"
                + FilenameUtils.EXTENSION_SEPARATOR_STR
                + FilenameUtils.getExtension(sourceFileName);

        source = ImageIO.read(sourceFile);
        destination = grayScale(source);

        ImageIO.write(destination, format, new File(destFileName));

    }

    private static BufferedImage grayScale(BufferedImage image) {

        int imageType = image.getColorModel().hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), imageType);

        ColorConvertOp grayOp = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        grayOp.filter(image, newImage);

        RescaleOp lowContrastOp = lowContrastOp(image.getColorModel());
        lowContrastOp.filter(newImage, newImage);

        return newImage;
    }

    // get image format in a file
    public static String getImageFormat(File imageFile) throws IOException {
        // create an image input stream from the specified file
        ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
        // get all currently registered readers that recognize the image format
        Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);
        if (!iterator.hasNext()) {
            throw new IOException("Unsupported image format");
        }
        // get the first reader
        ImageReader reader = iterator.next();
        String format = reader.getFormatName();
        // close stream
        iis.close();
        return format;
    }

    private static RescaleOp lowContrastOp(ColorModel colorModel){
        final float scaleFactor = 0.5f;
        final float offsetFactor = 128f; // half of 8-bit color component

        int numColors = colorModel.getNumColorComponents();
        int numComponents = colorModel.getNumComponents(); // same as numColors plus 1, if alpha channel present

        float scale[] = new float[numComponents];
        float offset[] = new float[numComponents];

        for(int i = 0; i < numColors; i++){
            scale[i] = scaleFactor;
            offset[i] = offsetFactor;
        }
        if(colorModel.hasAlpha()){
            scale[numColors] = 1f; // do not scale alpha channel
            offset[numColors] = 0f; // do not offset alpha channel
        }

        return new RescaleOp(scale, offset, null);
    }

    private static void showSupportedFormats(){
        Set<String> set = new HashSet<String>();
// Get list of all informal format names understood by the current set of registered readers
        String[] formatNames = ImageIO.getReaderFormatNames();
        for (int i = 0; i < formatNames.length; i++) {
            set.add(formatNames[i].toLowerCase());
        }
        System.out.println("Supported read formats: " + set);
        set.clear();
// Get list of all informal format names understood by the current set of registered writers
        formatNames = ImageIO.getWriterFormatNames();
        for (int i = 0; i < formatNames.length; i++) {
            set.add(formatNames[i].toLowerCase());
        }
        System.out.println("Supported write formats: " + set);
        set.clear();
// Get list of all MIME types understood by the current set of registered readers
        formatNames = ImageIO.getReaderMIMETypes();
        for (int i = 0; i < formatNames.length; i++) {
            set.add(formatNames[i].toLowerCase());
        }
        System.out.println("Supported read MIME types: " + set);
        set.clear();
// Get list of all MIME types understood by the current set of registered writers
        formatNames = ImageIO.getWriterMIMETypes();
        for (int i = 0; i < formatNames.length; i++) {
            set.add(formatNames[i].toLowerCase());
        }
        System.out.println("Supported write MIME types: " + set);
    }

    public static void main(String[] args) {

        showSupportedFormats();

        try {
            createLowContrastGrayscaleImageCopy("src\\main\\webapp\\resources\\images\\140927_n06160.jpg");
            createLowContrastGrayscaleImageCopy("src\\main\\webapp\\resources\\images\\source11.png");
            createLowContrastGrayscaleImageCopy("src\\main\\webapp\\resources\\images\\polka-prototip.gif");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

