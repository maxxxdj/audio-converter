package com.maxxxdj.audioconverter.service;

import com.maxxxdj.audioconverter.exception.FailedConversationException;
import com.maxxxdj.audioconverter.exception.WrongInputException;
import com.maxxxdj.audioconverter.model.AudioType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Optional;

import static com.maxxxdj.audioconverter.utils.Constants.*;

@Service
@Slf4j
public class ConverterImpl implements Converter{
    final InputProcessor inputProcessor;
    final Encoder encoder = new Encoder();

    final String outputPathFromConfig;

    public ConverterImpl(final InputProcessor inputProcessor, @Value("${custom.output-path}") String outputPathFromConfig) {
        this.inputProcessor = inputProcessor;
        this.outputPathFromConfig = outputPathFromConfig;
    }
    @Override
    public byte[] convertToBytes(final Object obj) {
        return SerializationUtils.serialize(obj);
    }

    @Override
    public File convertToFile(final String path) {

        return Optional.of(new File(path))
                .filter(File::exists)
                .orElseGet(() -> {
                    try {
                        File newFile = new File(path);
                        if (newFile.createNewFile()) {
                            log.info("File created: {}", newFile.getAbsolutePath());
                        } else {
                            log.info("File already exists: {} ", newFile.getAbsolutePath());
                        }
                        return newFile;
                    } catch (IOException e) {
                        throw new FailedConversationException(String.format("Failed to create file!: %s", e.getMessage()));
                    }
                });
    }


    @Override
    public File convertToTarget(final AudioType target, final String inputPath) {
         return switch (target) {
            case MP3 -> this.convert(inputPath, MP3_CODEC, MP3);
            case WAV -> this.convert(inputPath, WAV_CODEC, WAV);
            case FLAC, NULL -> null;
         };
    }
    private File convert(String inputPath, String codec, String outputFormat) {
        final MultimediaObject inputSource = getMultimediaSource(inputPath);
        final String outputPath = outputPathFromConfig + "converted" + "." + outputFormat;
        final File target = this.convertToFile(outputPath);
        final AudioAttributes audio = new AudioAttributes();
        audio.setCodec(codec);
        final EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat(outputFormat);
        attrs.setAudioAttributes(audio);
        try {
            encoder.encode(inputSource, target, attrs);
            log.info("Successfully converted to {}", outputPath);
            return convertToFile(outputPath);
        } catch (EncoderException e) {
            log.info("Conversation failed: {}", e.getMessage());
            throw new FailedConversationException(String.format("Conversion failed: %s", e.getMessage()));
        }
    }
    private MultimediaObject getMultimediaSource(final String input) {
        if (inputProcessor.processPath(input)) {
            boolean isLink = input.contains("http");
            if (isLink) {
                try {
                    return new MultimediaObject(URI.create(input).toURL());
                } catch (MalformedURLException e) {
                    log.warn("Bad URL provided for getting multimedia: {}", e.getMessage());
                    throw new WrongInputException("Bad URL provided for getting multimedia");
                }
            } else {
                return new MultimediaObject(this.convertToFile(input));
            }
        }
        throw new WrongInputException("Wrong Input of path to file!");
    }
}

