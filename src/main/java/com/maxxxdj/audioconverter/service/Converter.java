package com.maxxxdj.audioconverter.service;

import com.maxxxdj.audioconverter.model.AudioType;

import java.io.File;
import java.net.MalformedURLException;

public interface Converter {
    byte[] convertToBytes(final Object obj);
    File convertToFile(final String path);
    File convertToTarget(final AudioType target, final String inputPath);
}
