package com.maxxxdj.audioconverter.service.strategy;

import com.maxxxdj.audioconverter.model.FileCategory;
import com.maxxxdj.audioconverter.model.FileType;
import com.maxxxdj.audioconverter.model.UserRequest;
import com.maxxxdj.audioconverter.utils.Helper;
import org.springframework.util.SerializationUtils;

import java.io.File;

public interface Convertor {

    default byte[] convertToBytes(final Object obj) {
        return SerializationUtils.serialize(obj);
    }

    default File createFileFromPath(final String path) {
        return Helper.getFileFromPath(path);
    }
    File convert(final UserRequest userRequest, final String outputPath);

    default String getPath(String desiredPath, String outputFileName, String fileType) {
        return desiredPath  + outputFileName + "." + fileType.toLowerCase();
    }
    FileType getFileType();
    FileCategory getFileCategory();
}
