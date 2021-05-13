package com.scraping.scraping;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ScrapingService {

    private static final int SCROLL_DOWN = 10;
    SaveData saveData;

    public ScrapingService(SaveData saveData) {
        this.saveData = saveData;
    }

    public List<RestaurantDto> getRestaurants(String html) throws IOException, InterruptedException {
        confChromeDriver();
        WebDriver driver = getMainPage(html);
        Document doc = Jsoup.parse(driver.getPageSource());
        Elements body = doc.select("div.restaurantContainer");
        List<RestaurantDto> restaurants = getListOfRestaurants(driver, body);
        saveData.save(restaurants.toString());
        log.info("Scraped records: " + restaurants.size());
        return restaurants;
    }

    private List<RestaurantDto> getListOfRestaurants(WebDriver driver, Elements body) throws InterruptedException {
        List<RestaurantDto> restaurants = new ArrayList<>();
        for (Element e : body.select("div.restaurant-repeater")) {
            RestaurantDto rest = createRestaurantDto(driver, e);
            log.info("Create restaurant: " + rest.getName() + " address: " + rest.getAddress());
            restaurants.add(rest);
        }
        return restaurants;
    }

    private WebDriver getMainPage(String html) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(html);
        for (int i = 0; i < SCROLL_DOWN; i++) {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
        }
        Thread.sleep(3000);
        return driver;
    }

    private RestaurantDto createRestaurantDto(WebDriver driver, Element e) throws InterruptedException {
        RestaurantDto rest = new RestaurantDto();
        rest.setName(e.select("img").attr("alt"));
        rest.setWeb("https://glodny.pl" + e.select("div.left").select("a").attr("href"));

        driver.get(rest.getWeb());
        Thread.sleep(500);
        Document document = Jsoup.parse(driver.getPageSource());
        Elements data = document.select("div.details");
        try {
            rest.setAddress(data.select("span.correct").get(2).text());
            rest.setWorkingHours(data.select("span.correct").get(0).text());
            rest.setTimeToDelivery(data.select("div.time").select("span.ng-binding").text());
        } catch (IndexOutOfBoundsException ex) {
            log.warn(ex.getMessage());
            return rest;
        }
        return rest;
    }

    private void confChromeDriver() {
        System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
    }
}
