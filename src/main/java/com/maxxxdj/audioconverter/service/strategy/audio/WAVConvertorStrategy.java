package com.maxxxdj.audioconverter.service.strategy.audio;

import com.maxxxdj.audioconverter.model.FileCategory;
import com.maxxxdj.audioconverter.model.FileType;
import com.maxxxdj.audioconverter.model.UserRequest;
import com.maxxxdj.audioconverter.service.strategy.Convertor;
import com.maxxxdj.audioconverter.service.InputProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;

import static com.maxxxdj.audioconverter.utils.Constants.WAV_CODEC;

/**
 * @author martin.miloshev
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "custom.convertors.wav.enabled", havingValue = "true")
public class WAVConvertorStrategy extends AudioConvertor {

    @Override
    public File convert(final UserRequest userRequest, final String desiredPath) {
        final MultimediaObject inputSource = getMultimediaFromHTTPRequest(userRequest.getFilePath());
        final String outputFileName = InputProcessor.getFileName(userRequest.getFilePath());
        final String outputPath = getPath(desiredPath, outputFileName, FileType.WAV.name());
        final File target = createFileFromPath(outputPath);
        EncodingAttributes encAttrs = getEncAttributes(WAV_CODEC, FileType.WAV.name());
        return convertFile(inputSource, target, encAttrs, outputPath);
    }

    @Override
    public FileType getFileType() {
        return FileType.WAV;
    }
}
