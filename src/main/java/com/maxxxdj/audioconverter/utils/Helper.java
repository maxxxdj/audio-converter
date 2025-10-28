package com.maxxxdj.audioconverter.utils;

import com.maxxxdj.audioconverter.exception.FailedConversationException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author martin.miloshev
 */
@Slf4j
public class Helper {

    public static File getFileFromPath(final String path){
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
}
