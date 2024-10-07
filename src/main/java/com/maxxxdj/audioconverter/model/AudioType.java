package com.maxxxdj.audioconverter.model;

import com.maxxxdj.audioconverter.exception.WrongInputException;

import java.util.Arrays;

public enum AudioType {
    MP3,
    WAV,
    FLAC;

    public static AudioType getType(final String input){
        return Arrays.stream(AudioType.values())
                .filter(el->el.toString().equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(()-> new WrongInputException(String.format("Wrong input passed %s", input)));
    }
}
