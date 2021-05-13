package com.scraping.scraping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;

@Controller
@Slf4j
public class SaveData {

    private static final String FILE = getDate() + " scraping.txt";

    private static String getDate() {
        return LocalDateTime.now().toString()
                .replace(".", ",")
                .replaceAll(":", "-");
    }

    protected void save(String text) {
        try (
                Writer writer = new BufferedWriter(new FileWriter(FILE, true))) {
            writer.append(text);
        } catch (IOException e) {
            log.warn("Save: " + e.getMessage());
        }
    }
}
