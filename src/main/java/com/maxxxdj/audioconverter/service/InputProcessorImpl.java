package com.maxxxdj.audioconverter.service;

import org.springframework.stereotype.Service;

@Service
public class InputProcessorImpl implements InputProcessor{

    @Override
    public Boolean processPath(final String path) {
        return null != path && !path.isBlank() && !path.isEmpty();
    }
}
