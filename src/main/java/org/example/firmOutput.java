package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.*;
import org.openqa.selenium.JavascriptExecutor;

public class firmOutput {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

// 2.3 вывод 20 фирм с наибольшим количеством объявлений

        driver.get("http://auto.drom.ru/");

        //переход в выбор региона
        WebElement element = driver.findElement(By.xpath("//a[@data-ga-stats-name='geoOverCity']"));
        element.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //ввод ключа поиска Приморский край
        element = driver.findElement(By.xpath("//input[@type='text']"));
        element.click();
        element.sendKeys("Приморский край");

        //выбор фильтра
        element = driver.findElement(By.xpath("//div[@data-ga-stats-name='found_city']"));
        element.click();

        element = driver.findElement(By.cssSelector("input[role='combobox'][placeholder='Марка']"));

        //клик по полю поиска марки
        element.click();

        element = driver.findElement(By.xpath("//div[@data-ftid='component_select_dropdown']"));

        WebElement div = driver.findElement(By.xpath("//div[@data-ftid='component_select_dropdown']"));

        // Создайте объект JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Прокрутите div блок
        // Получите высоту div блока
        long divHeight = (Long) js.executeScript("return arguments[0].scrollHeight", div);
        List<WebElement> allItems = new ArrayList<>();

        // Прокрутите div блок на 100-200 пикселей каждые 0.5 секунды
        for (int i = 0; i < divHeight; i += 500) {
            js.executeScript("arguments[0].scrollTop = " + i, div);
            try {
                Thread.sleep(100); // Пауза в 0.5 секунды
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           // List<WebElement> dropdownItems = driver.findElements(By.xpath(".//div[@role='option']"));

            List<WebElement> dropdownItems = div.findElements(By.xpath(".//div[@role='option']"));

            System.out.println(dropdownItems);

            for (WebElement item : dropdownItems) {
                String text = item.getText();
                if (!text.isEmpty()) {
                    allItems.add(item);
                }
            }

        }
        for (WebElement item : allItems) {
            System.out.println(item.getText());
        }

        }

    }

