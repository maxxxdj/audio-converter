package com.maxxxdj.audioconverter.service;

import com.maxxxdj.audioconverter.model.FileCategory;
import com.maxxxdj.audioconverter.model.UserRequest;
import com.maxxxdj.audioconverter.service.strategy.Convertor;
import com.maxxxdj.audioconverter.service.strategy.DefaultStrategy;
import com.maxxxdj.audioconverter.service.strategy.audio.DefaultAudioStrategy;
import com.maxxxdj.audioconverter.service.strategy.image.CommonImageConvertor;
import com.maxxxdj.audioconverter.service.strategy.video.DefaultVideoStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
public class ConvertMediator {

    private final String outputPathFromConfig;
    private final StrategyContainer strategyContainer;
    private final ApplicationContext applicationContext;


    public ConvertMediator(@Value("${custom.output-path}") final String outputPathFromConfig, StrategyContainer strategyContainer, ApplicationContext applicationContext){
        this.outputPathFromConfig = outputPathFromConfig;
        this.strategyContainer = strategyContainer;
        this.applicationContext = applicationContext;
    }

    public Optional<File> convertToTarget(final UserRequest userRequest) {
        return Optional.ofNullable(strategyContainer.getConvertorsContainer()
                .getOrDefault(userRequest.getInputFileCategory(), new ArrayList<>())
                .stream()
                .filter(el -> el.getFileType().equals(userRequest.getInputFileType()))
                .findFirst()
                .orElse(getDefaultCategoryConvertor(userRequest.getInputFileCategory()))
                .convert(userRequest, outputPathFromConfig));
         }

         private Convertor getDefaultCategoryConvertor(FileCategory fileCategory) {
            return switch (fileCategory) {
                case VIDEO -> applicationContext.getBean(DefaultVideoStrategy.class);
                case AUDIO -> applicationContext.getBean(DefaultAudioStrategy.class);
                case IMAGE -> applicationContext.getBean(CommonImageConvertor.class);
                case UNKNOWN -> applicationContext.getBean(DefaultStrategy.class);
            };
         }
    }

