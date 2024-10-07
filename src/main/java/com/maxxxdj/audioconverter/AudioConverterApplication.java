package com.maxxxdj.audioconverter;

import com.maxxxdj.audioconverter.service.InputProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class AudioConverterApplication {

    private final InputProcessor inputProcessor;

    public AudioConverterApplication(InputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }

    public static void main(String[] args) {
        SpringApplication.run(AudioConverterApplication.class, args);
    }
}
