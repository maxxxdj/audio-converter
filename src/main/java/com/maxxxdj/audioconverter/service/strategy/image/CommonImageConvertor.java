package com.maxxxdj.audioconverter.service.strategy.image;

import com.maxxxdj.audioconverter.model.FileCategory;
import com.maxxxdj.audioconverter.model.FileType;
import com.maxxxdj.audioconverter.model.UserRequest;
import com.maxxxdj.audioconverter.utils.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author martin.miloshev
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "custom.convertors.common.enabled", havingValue = "true")
public class CommonImageConvertor extends ImageConverter {
    @Override
    public File convert(final UserRequest userRequest, final String outputPath) {
        File inputFile = Helper.getFileFromPath(userRequest.getFilePath());
        final String outputFileName = userRequest.getFilePath().replaceAll(".*/([^/]+)\\.(png|jpg|jpeg|heic)$", "$1");
        String outputFilePath = getPath(outputPath, outputFileName, userRequest.getDesiredOutputFile().name().toLowerCase());
        File targetFile = Helper.getFileFromPath(outputFilePath);
        try {
            convertImage(userRequest, inputFile, targetFile);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return targetFile;
    }

    @Override
    public FileType getFileType() {
        return FileType.COMMON_IMAGE;
    }

    @Override
    public FileCategory getFileCategory() {
        return FileCategory.IMAGE;
    }

    public static void convertImage(final UserRequest userRequest, File inputFile, File outputFile) throws IOException {
        BufferedImage image = ImageIO.read(inputFile);
        if (image == null) {
            throw new IOException("Unsupported image format: " + inputFile.getName());
        }

        String outputFormat = userRequest.getDesiredOutputFile().name().toLowerCase();
        if (!ImageIO.write(image, outputFormat, outputFile)) {
            throw new IOException("Could not write image in format: " + outputFormat);
        }
    }
}
