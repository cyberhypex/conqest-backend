package com.conqest.conq.Scrapping;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

public class CodeChefScrapping {
    public static void main(String[] args) {
        // 1️⃣ Setup
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); //Web Window does not open
        WebDriver driver = new ChromeDriver(options);

        try {
            //loading the page
            driver.get("https://www.codechef.com/contests");


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div._data__container_7s2sw_533")));

            // Find contest block via ur css-selector
            List<WebElement> contests = driver.findElements(By.cssSelector("div._data__container_7s2sw_533"));
            System.out.println("Number of contests found: " + contests.size());

            //Scrap data
            for (WebElement contest : contests) {
                String link = contest.findElement(By.cssSelector("a")).getAttribute("href");
                String title = contest.findElement(By.cssSelector("a span")).getText();
                List<WebElement> timeElements = contest.findElements(By.cssSelector("div._timer__container_7s2sw_590 p"));
                String time = timeElements.stream()
                        .map(WebElement::getText)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("Time Not Found");

                System.out.println("\n-----------------------------");
                System.out.println("Link : " + link);
                System.out.println("Title: " + title);
                System.out.println("Time : " + time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Close driver
            driver.quit();
        }
    }
}
