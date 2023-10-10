package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

public class Auth {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

// 2.2 авторизация+добавление в избранное

        driver.get("http://auto.drom.ru/");

        //переход на страницу авторизации
        WebElement element = driver.findElement(By.xpath("//a[@data-ga-stats-name='auth_block_login']"));
        element.click();

        //Ввод логина
        element = driver.findElement(By.xpath("//input[@name='sign']"));
        element.click();
        element.sendKeys("thebluered");

        //Ввод пароля
        element = driver.findElement(By.xpath("//input[@name='password']"));
        element.click();
        element.sendKeys("8Ylre7bIym25");

        //клик для авторизации
        element = driver.findElement(By.xpath("//button[@type='submit']"));
        element.click();

        List<WebElement> elements = driver.findElements(By.xpath("//div[@data-bulletin-list='true']"));

        element = elements.get(0);
        element.click();

        element = driver.findElement(By.xpath("//div[@data-ga-stats-track-click='favorite']"));
        element.click();

        driver.quit();
    }

}