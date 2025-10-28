package com.maxxxdj.audioconverter.service.strategy.audio;

import com.maxxxdj.audioconverter.exception.FailedConversationException;
import com.maxxxdj.audioconverter.exception.WrongInputException;
import com.maxxxdj.audioconverter.model.FileCategory;
import com.maxxxdj.audioconverter.service.InputProcessor;
import com.maxxxdj.audioconverter.service.strategy.Convertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

/**
 * @author martin.miloshev
 */

@Slf4j
public abstract class AudioConvertor implements Convertor {
    final protected Encoder encoder = new Encoder();

    protected File convertFile(MultimediaObject inputSource, File target, EncodingAttributes encAttrs, String outputPath) {
        try {
            encoder.encode(inputSource, target, encAttrs);
            log.info("Successfully converted to {}", outputPath);
            return createFileFromPath(outputPath);
        } catch (EncoderException e) {
            log.info("Conversation failed: {}", e.getMessage());
            throw new FailedConversationException(String.format("Conversion failed: %s", e.getMessage()));
        }
    }

    protected EncodingAttributes getEncAttributes(final String codec, final String audioType) {
        final EncodingAttributes attrs = new EncodingAttributes();
        final AudioAttributes audio = new AudioAttributes();
        audio.setCodec(codec);
        attrs.setOutputFormat(audioType);
        attrs.setAudioAttributes(audio);
        return attrs;
    }

    protected MultimediaObject getMultimediaFromHTTPRequest(final String inputPath) {
        if (InputProcessor.isPathValid(inputPath)) {
            boolean isLink = inputPath.contains("http");
            if (isLink) {
                try {
                    return new MultimediaObject(URI.create(inputPath).toURL());
                } catch (MalformedURLException e) {
                    log.warn("Bad URL provided for getting multimedia: {}", e.getMessage());
                    throw new WrongInputException("Bad URL provided for getting multimedia");
                }
            } else {
                return new MultimediaObject(this.createFileFromPath(inputPath));
            }
        }
        throw new WrongInputException("Wrong Input of path to file!");
    }
    @Override
    public FileCategory getFileCategory() {
        return FileCategory.AUDIO;
    }
}
