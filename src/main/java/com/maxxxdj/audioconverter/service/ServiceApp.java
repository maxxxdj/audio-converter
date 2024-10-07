package com.maxxxdj.audioconverter.service;

import com.maxxxdj.audioconverter.model.AudioType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@Slf4j
public class ServiceApp {
    private final Converter converter;

    public ServiceApp(Converter converter) {
        this.converter = converter;
    }

    @Bean
    @ConditionalOnProperty(name = "custom.is_command_line_enabled", havingValue = "true", matchIfMissing = false)
    CommandLineRunner commandLineRunner() {
        return args -> {
            log.info("Application is started. Proceeding to command line interface.");
            System.out.println("Please enter the input file path or URL");
            Scanner scanner = new Scanner(System.in);
            String inputPath = scanner.nextLine().trim();
            log.info("File path provided by user: " + inputPath);

            System.out.println("Please enter the desired output format (MP3, WAV)");
            String format = scanner.nextLine().trim().toUpperCase();
            log.info("Desired output format is: " + format);

            converter.convertToTarget(AudioType.getType(format), inputPath);
        };
    }
}
