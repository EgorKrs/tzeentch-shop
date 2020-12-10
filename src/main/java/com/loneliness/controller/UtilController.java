package com.loneliness.controller;

import com.loneliness.entity.BookStatus;
import com.loneliness.entity.domain.Genre;
import com.loneliness.util.json_parser.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UtilController {
    @GetMapping()
    public String getIndex(Map<String, Object> model) throws IOException {
        model.put("AllGenre", JsonParser.mapToJson(Genre.values()));
        model.put("AllBookStatus", JsonParser.mapToJson(BookStatus.values()));
        return "index";
    }
}
