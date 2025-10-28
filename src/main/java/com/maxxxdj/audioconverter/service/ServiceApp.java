package com.maxxxdj.audioconverter.service;

import com.maxxxdj.audioconverter.model.FileCategory;
import com.maxxxdj.audioconverter.model.FileType;
import com.maxxxdj.audioconverter.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@Slf4j
public class ServiceApp {
    private final ConvertMediator mediator;

    public ServiceApp(ConvertMediator mediator) {
        this.mediator = mediator;
    }

    @Bean
    @ConditionalOnProperty(name = "custom.is_command_line_enabled", havingValue = "true", matchIfMissing = false)
    CommandLineRunner commandLineRunner() {
        log.info("Application is started. Proceeding to command line interface.");
        return args -> {
            while (true) {
                System.out.println("Please enter the input file path or URL");
                Scanner scanner = new Scanner(System.in);
                String inputPath = scanner.nextLine().trim();
                log.info("File path provided by user: {}", inputPath);
                if (!InputProcessor.isPathValid(inputPath)) {
                    log.warn("Wrong path provided by user: {}", inputPath);
                    continue;
                }
                FileType inputFileType = InputProcessor.getFileType(inputPath);
                if (inputFileType.equals(FileType.UNKNOWN)) {
                    log.warn("Wrong file type provided by user: {}", inputFileType.name());
                    continue;
                }
                System.out.printf("Available formats for conversion : %s \nPlease select the desired one: \n", String.join(", ", inputFileType.getOutputFormats()));
                String desiredFormat = scanner.nextLine().trim().toUpperCase();
                log.info("Desired output format is: {}", desiredFormat);
                FileType desiredOutputFileType = FileType.getType(desiredFormat);
                if (desiredOutputFileType.equals(FileType.UNKNOWN)) {
                    log.warn("Wrong desired output file type: {}", desiredOutputFileType.name());
                    continue;
                }
                final UserRequest userRequest = UserRequest.builder()
                        .filePath(inputPath)
                        .inputFileType(inputFileType)
                        .inputFileCategory(inputFileType.getFileCategory())
                        .desiredOutputFile(desiredOutputFileType)
                        .build();
                try {
                    mediator.convertToTarget(userRequest);
                } catch (Exception e) {
                    log.warn("Failed to convert to target file.", e.getMessage());
                }
            }
        };
    }
}
