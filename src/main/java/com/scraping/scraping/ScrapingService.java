package com.scraping.scraping;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JsoupRun {

    public List<RestaurantDto> getRestaurants(String html) throws IOException, InterruptedException {
//        String html = "https://glodny.pl/gdansk/restauracja-dowozem/80-831";
        List<RestaurantDto> restaurants = new ArrayList<>();

        System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait();
        driver.get(html);
        Document doc = Jsoup.parse(driver.getPageSource());
        Elements body = doc.select("div.restaurantContainer");
        Elements list = body.select("div.restaurant-repeater");
        for (Element e : list) {
            RestaurantDto rest = new RestaurantDto();
            rest.setName(e.select("img").attr("alt"));
            rest.setWeb("https://glodny.pl" + e.select("div.left").select("a").attr("href"));

            driver.get(rest.getWeb());
            wait.until(ExpectedConditions.
            Thread.sleep(200);
            Document document = Jsoup.parse(driver.getPageSource());
            Elements data = document.select("div.details");
            rest.setAddress(data.select("span.correct").get(2).text());
            rest.setWorkingHours(data.select("span.correct").get(0).text());
            rest.setTimeToDelivery(data.select("div.time").select("span.ng-binding").text());

            log.info("Create restaurant: " + rest);
            restaurants.add(rest);
        }
        return restaurants;
    }
}
