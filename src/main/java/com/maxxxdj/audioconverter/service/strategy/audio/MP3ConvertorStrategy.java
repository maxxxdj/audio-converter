package com.maxxxdj.audioconverter.service.strategy.audio;

import com.maxxxdj.audioconverter.model.FileType;
import com.maxxxdj.audioconverter.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;

import static com.maxxxdj.audioconverter.utils.Constants.MP3_CODEC;

/**
 * @author martin.miloshev
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "custom.convertors.mp3.enabled", havingValue = "true")
public class MP3ConvertorStrategy extends AudioConvertor {
    @Value("${custom.convertors.mp3.bitrate}")
    private Integer bitrateFromConfig;

    @Override
    public File convert(final UserRequest userRequest, final String desiredPath) {
        final MultimediaObject inputSource = getMultimediaFromHTTPRequest(userRequest.getFilePath());
        final String outputFileName = userRequest.getFilePath().replaceAll(".*/([^/]+)\\.(mp3|wav|flac|ogg|m4a|aac|aiff|wma)$", "$1");
        final String outputPath = getPath(desiredPath, outputFileName, FileType.MP3.name());
        final File target = createFileFromPath(outputPath);
        EncodingAttributes encAttrs = getEncAttributes(MP3_CODEC, FileType.MP3.name());
        encAttrs.getAudioAttributes().ifPresent(el -> el.setBitRate(bitrateFromConfig));
        return convertFile(inputSource, target, encAttrs, outputPath);
    }

    @Override
    public FileType getFileType() {
        return FileType.MP3;
    }
}
