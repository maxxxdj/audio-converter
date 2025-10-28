package com.maxxxdj.audioconverter.service;

import com.maxxxdj.audioconverter.model.FileType;
import org.springframework.stereotype.Service;

@Service
public class InputProcessor {

    public static FileType getFileType(String path) {
        final String fileExtension = getFileExtension(path);
        return FileType.getType(fileExtension);
    }

    private static String getFileExtension(String path) {
        int dotIndex = path.lastIndexOf('.');
        int sepIndex = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        return dotIndex > sepIndex && dotIndex + 1 < path.length()? path.substring(dotIndex + 1) : "";
    }
    public static String getFileName(String path) {
        if (path == null || path.isBlank()) return "";

        int slashIndex = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        int dotIndex = path.lastIndexOf('.');

        if (dotIndex == -1 || dotIndex < slashIndex) {
            dotIndex = path.length();
        }

        return path.substring(slashIndex + 1, dotIndex);
    }

    public static Boolean isPathValid(final String path) {
        return null != path && !path.isBlank() && path.contains(".");
    }
}
