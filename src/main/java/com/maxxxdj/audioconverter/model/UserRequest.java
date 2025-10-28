package com.maxxxdj.audioconverter.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author martin.miloshev
 */
@Data
@Builder
public class UserRequest {
    private String filePath;
    private FileType desiredOutputFile;
    private FileType inputFileType;
    private FileCategory inputFileCategory;
}
