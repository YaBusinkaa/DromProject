package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));


// 2.1 проверка фильтрации продажи авто

        driver.get("http://auto.drom.ru/");

        WebElement element = driver.findElement(By.cssSelector("input[role='combobox'][placeholder='Марка']"));

        //клик по полю поиска марки
        element.click();
        element.sendKeys("Toyota");

        //клик на тойоту
        element = driver.findElement(By.cssSelector("div[role='option'][aria-selected='false']"));
        element.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //клик по полю модель
        element = driver.findElement(By.cssSelector("input[role='combobox'][placeholder='Модель']"));
        element.click();

        //element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[role='combobox'][placeholder='Модель']")));
        element.sendKeys("Harrier");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //клик на Harrier
        element = driver.findElement(By.cssSelector("div[role='option'][aria-selected='false']"));
        element.click();

        //клик по полю топливо
        element = driver.findElement(By.xpath("//button[@type='button' and @data-ftid='component_select_button' and text()='Топливо']"));
        element.click();

        //клик по гибрид
        element = driver.findElement(By.xpath("//div[@role='option'][text()='Гибрид']"));
                element.click();

        //клик по непроданные
        element = driver.findElement(By.xpath("//label[@for='sales__filter_unsold'][text()='Непроданные']"));
        if (!element.isSelected()) {
            element.click();
        }
        //клик по полю год от
        element = driver.findElement(By.xpath("//button[@type='button' and @data-ftid='component_select_button' and text()='Год от']"));
        element.click();

        //клик выбор 2007 года
        element = driver.findElement(By.xpath("//div[@role='option'][text()='2007']"));
        element.click();


        element = driver.findElement(By.xpath("//button[@data-ftid='sales__filter_advanced-button']"));
        element.click();

        element = driver.findElement(By.cssSelector("input[aria-autocomplete='list'][placeholder='от, км']"));
        element.click();
        element.sendKeys("1");

        //принятие фильтрации
        element = driver.findElement(By.xpath("//button[@data-ftid='sales__filter_submit-button']"));
        element.click();

        //проверка на снятые с продажи
        List<WebElement> elements = driver.findElements(By.xpath("//a[@data-ftid='bulls-list_bull']"));
        isLineThrough(elements);

        //проверка на наличие пробега
        hasMileage(elements);

        //проверка на год не меньше 2007
        correctYear(elements);


        //переход на 2 страницу
        element = driver.findElement(By.xpath("//a[@data-ftid='component_pagination-item'][text()='2']"));
        element.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        List<WebElement>  elements = driver.findElements(By.cssSelector("*"));

        elements = driver.findElements(By.xpath("//a[@data-ftid='bulls-list_bull']"));
        //проверка на снятые с продажи
        isLineThrough(elements);

        //проверка на наличие пробега
        hasMileage(elements);

        //проверка на год не меньше 2007
        correctYear(elements);

        driver.quit();

    }
    public static void isLineThrough(List<WebElement> elements){

        for (WebElement element : elements) {
            String textDecoration = element.getCssValue("text-decoration");
            if (textDecoration.contains("line-through")) {
                System.out.println("test failed. Some car was sold");
                return;
            }
        }
    }
    public static void hasMileage(List<WebElement> elements){
       int counter = 0;
       for (WebElement element : elements) {

       List<WebElement> spanElements = element.findElements(By.tagName("span"));
           for (WebElement span : spanElements) {
               String spanText = span.getText();
               if (spanText.contains(" км")) {
                   counter++;
               }
           }

        }
        if (counter != elements.size()) {
            System.out.println("test failed. Some car has no mileage");
            return;
        }
    }
    public static void correctYear (List<WebElement> elements){
        int year = 2007;
        for (WebElement element : elements) {

            WebElement spanElement = element.findElement(By.xpath(".//span[@data-ftid='bull_title']"));

            String spanText = spanElement.getText();
            String[] parts = spanText.split(", ");
            int carYear = Integer.parseInt(parts[1]);


            if (carYear <= year) {
                System.out.println("test failed. The year of the car doesn`t match");
                return;
            }
        }
    }
}