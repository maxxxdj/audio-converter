package com.maxxxdj.audioconverter.model;

import java.util.Arrays;
import java.util.List;

public enum FileType {

    /** Video Files */
    AVI,
    MP4,
    MOV(List.of("AVI", "MP4"),FileCategory.VIDEO),
    FLV,
    WMV,

    /** Audio Files */
    MP3(List.of("WAV", "FLAC", "AAC", "OGG", "M4A", "AIFF"), FileCategory.AUDIO),
    WAV(List.of("MP3", "FLAC", "AAC", "OGG", "M4A", "AIFF"), FileCategory.AUDIO),
    FLAC(List.of("MP3", "WAV", "AAC", "OGG", "M4A", "AIFF"), FileCategory.AUDIO),
    AAC(List.of("MP3", "WAV", "FLAC", "OGG", "M4A", "AIFF"), FileCategory.AUDIO),
    OGG(List.of("MP3", "WAV", "FLAC", "AAC", "M4A", "AIFF"), FileCategory.AUDIO),
    M4A(List.of("MP3", "WAV", "FLAC", "AAC", "OGG", "AIFF"), FileCategory.AUDIO),
    AIFF(List.of("MP3", "WAV", "FLAC", "AAC", "OGG", "M4A"), FileCategory.AUDIO),

    /** Image files*/
    JPEG(List.of("PNG", "GIF", "BMP", "TIFF", "PNM"), FileCategory.IMAGE),
    JPG(List.of("PNG", "GIF", "BMP", "TIFF", "PNM"), FileCategory.IMAGE),
    PNG(List.of("JPEG", "JPG", "GIF", "BMP", "TIFF", "PNM"), FileCategory.IMAGE),
    GIF(List.of("PNG", "JPEG", "JPG", "BMP", "TIFF", "PNM"), FileCategory.IMAGE),
    BMP(List.of("PNG", "JPEG", "JPG", "GIF", "TIFF", "PNM"), FileCategory.IMAGE),
    TIFF(List.of("PNG", "JPEG", "JPG", "GIF", "BMP", "PNM"), FileCategory.IMAGE),
    PSD(List.of("PNG", "JPEG", "JPG", "GIF", "BMP", "TIFF", "PNM"), FileCategory.IMAGE),
    PNM(List.of("PNG", "JPEG", "JPG", "GIF", "BMP", "TIFF"), FileCategory.IMAGE),
    COMMON_IMAGE,
    UNKNOWN;

    private List<String> outputFormats;
    private FileCategory category;

    FileType() {
        this.outputFormats = List.of();
    }

    FileType(final List<String> outputFormats, final FileCategory category) {
        this.outputFormats = outputFormats;
        this.category = category;
    }

    public List<String> getOutputFormats() {
        return outputFormats;
    }

    public FileCategory getFileCategory() {
        return category;
    }

    public static FileType getType(final String input){
        return Arrays.stream(FileType.values())
                .filter(el -> el.toString().equalsIgnoreCase(input))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
