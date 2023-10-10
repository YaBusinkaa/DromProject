package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

        // Создание объекта JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Получение высоты div блока
        long divHeight = (Long) js.executeScript("return arguments[0].scrollHeight", div);
        List<String> allItems = new ArrayList<>();

        // Прокрутка div блок на 100-200 пикселей каждые 0.5 секунд
        for (int i = 0; i < divHeight; i += 500) {
            js.executeScript("arguments[0].scrollTop = " + i, div);
            try {
                Thread.sleep(100); // Пауза в 0.5 секунды
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebDriverWait waitElement = new WebDriverWait(driver, Duration.ofSeconds(3));
            waitElement.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@role='option']")));

            List<WebElement> dropdownItems = div.findElements(By.xpath(".//div[@role='option']"));
            for (WebElement item : dropdownItems) {
                String text = item.getText();
                if (!text.isEmpty()) {
                    allItems.add(text);
                }
            }

        }

        Set<String> uniqueItems = new LinkedHashSet<>(allItems);

        Map<String, Integer> brandCounts = new TreeMap<>();

        for (String item : uniqueItems) {
            if (item.contains("(")) {
                String[] parts = item.split("\\(");
                String brand = parts[0].trim();
                if (parts.length > 1) {
                    String numberString = parts[1].replaceAll("[^0-9]", "");
                    int count = Integer.parseInt(numberString);
                    brandCounts.put(brand, count);
                }
            }
        }

// Сортировка по значению
        List<Map.Entry<String, Integer>> list = new ArrayList<>(brandCounts.entrySet());
        list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        Map<String, Integer> sortedBrandCounts = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedBrandCounts.put(entry.getKey(), entry.getValue());
        }

// Вывод отсортированного ассоциативного массива
        System.out.println("| Фирма | Количество объявлений |");
        System.out.println("|-------|----------------------|");
        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedBrandCounts.entrySet()) {
            System.out.println("| " + entry.getKey() + " | " + entry.getValue() + " |");
            count++;
            if (count >= 20) {
                break;
            }
        }

    }

}