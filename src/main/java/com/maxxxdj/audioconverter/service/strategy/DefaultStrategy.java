package com.maxxxdj.audioconverter.service.strategy;

import com.maxxxdj.audioconverter.model.FileCategory;
import com.maxxxdj.audioconverter.model.FileType;
import com.maxxxdj.audioconverter.model.UserRequest;

import java.io.File;

/**
 * @author martin.miloshev
 */
public class DefaultStrategy implements Convertor {
    @Override
    public File convert(final UserRequest userRequest, String outputPath) {
        return null;
    }

    @Override
    public FileType getFileType() {
        return FileType.UNKNOWN;
    }

    @Override
    public FileCategory getFileCategory() {
        return FileCategory.UNKNOWN;
    }
}
