package com.maxxxdj.audioconverter.service;

import com.maxxxdj.audioconverter.model.FileCategory;
import com.maxxxdj.audioconverter.model.FileType;
import com.maxxxdj.audioconverter.service.strategy.Convertor;
import com.maxxxdj.audioconverter.service.strategy.audio.MP3ConvertorStrategy;
import com.maxxxdj.audioconverter.service.strategy.audio.MP4ConvertorStrategy;
import com.maxxxdj.audioconverter.service.strategy.audio.WAVConvertorStrategy;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author martin.miloshev
 */
@Component
@Slf4j
@Data
public class StrategyContainer {
    private final List<Convertor> convertors;
    private final Map<FileCategory, List<Convertor>> convertorsContainer = new HashMap<>();

    public StrategyContainer(final List<Convertor> convertors) {
        this.convertors = convertors;
    }

    @PostConstruct
    void init() {
        for (Convertor convertor : convertors) {
            final FileCategory category = convertor.getFileCategory();

            convertorsContainer
                    .computeIfAbsent(category, k -> new ArrayList<>())
                    .add(convertor);
        }

        //TODO Log loading
    }
}
