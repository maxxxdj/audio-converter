package com.maxxxdj.audioconverter.web;

import com.maxxxdj.audioconverter.model.AudioType;
import com.maxxxdj.audioconverter.service.Converter;
import com.maxxxdj.audioconverter.service.InputProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling request passed to @Converter.
 * @Param @path - the path or URL for the desired file to be converted.
 * @Param @outputFormat - the desired output format for conversion.
 */

@RestController
@ConditionalOnProperty(name = "custom.web.is_rest_api_enabled", havingValue = "true")
@RequestMapping("/api")
public class Controller {

    private final InputProcessor inputProcessor;
    private final Converter converter;

    public Controller(InputProcessor inputProcessor, Converter converter) {
        this.inputProcessor = inputProcessor;
        this.converter = converter;
    }

    @PostMapping("/convertTo")
    public ResponseEntity<?> convertTo(@RequestParam final String path, @RequestParam final String outputFormat){
        if(inputProcessor.processPath(path)){
        return ResponseEntity.ok(converter.convertToTarget(AudioType.getType(outputFormat), path));
        } else {
        return ResponseEntity.badRequest().body(String.format("Wrong input param %s", path));
        }
    }
}
