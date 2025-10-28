package com.maxxxdj.audioconverter.service.strategy.audio;

import com.maxxxdj.audioconverter.model.FileCategory;
import com.maxxxdj.audioconverter.model.FileType;
import com.maxxxdj.audioconverter.model.UserRequest;
import com.maxxxdj.audioconverter.service.strategy.Convertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;

import static com.maxxxdj.audioconverter.utils.Constants.MP4_AUDIO_CODEC;

/**
 * @author martin.miloshev
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "custom.convertors.mp3.enabled", havingValue = "true")
public class MP4ConvertorStrategy extends AudioConvertor {

    @Override
    public File convert(final UserRequest userRequest, final String desiredPath) {
        final MultimediaObject inputSource = getMultimediaFromHTTPRequest(userRequest.getFilePath());
        final String outputFileName = userRequest.getFilePath().replaceAll(".*/([^/]+)\\.(mp3|wav|flac|ogg|m4a|aac|aiff|wma)$", "$1");
        final String outputPath = getPath(desiredPath, outputFileName, FileType.MP4.name());
        final File target = createFileFromPath(outputPath);
        EncodingAttributes encAttrs = getEncAttributes(MP4_AUDIO_CODEC, FileType.MP4.name());
        return convertFile(inputSource, target, encAttrs, outputPath);
    }

    @Override
    public FileType getFileType() {
        return FileType.MP4;
    }
}
