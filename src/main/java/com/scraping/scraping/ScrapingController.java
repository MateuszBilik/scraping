package com.scraping.scraping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ScrapingController {

    private final ScrapingService scrapingService;

    public ScrapingController(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @GetMapping
    public List<RestaurantDto> getWebSite(@RequestParam String web){
        log.info("Scraping web is: " + web);
        try {
            return scrapingService.getRestaurants(web);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            log.warn("Problem with your website" + web);
        }
        return new ArrayList<>();
    }
}
