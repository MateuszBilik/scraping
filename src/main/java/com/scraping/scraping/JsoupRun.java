package com.scraping.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;


public class JsoupRun {

    public static void main(String[] args) throws IOException, InterruptedException {
        String html = "https://glodny.pl/gdansk/restauracja-dowozem/80-831";

        System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(html);
        Document doc = Jsoup.parse(driver.getPageSource());
        Elements body = doc.select("div.restaurantContainer");
        Elements list = body.select("div.restaurant-repeater");
        for (Element e : list) {
            RestaurantDto rest = new RestaurantDto();
            rest.setName(e.select("img").attr("alt"));
            rest.setWeb("https://glodny.pl" + e.select("div.left").select("a").attr("href"));

            driver.get(rest.getWeb());
            Thread.sleep(200);
            Document document = Jsoup.parse(driver.getPageSource());
            Elements data = document.select("div.details");
            rest.setAddress(data.select("span.correct").get(2).text());
            rest.setWorkingHours(data.select("span.correct").get(0).text());
            rest.setTimeToDelivery(data.select("div.time").select("span.ng-binding").text());

            System.out.println(rest);
            System.out.println("-------------------------------------------------");


        }

    }
}
