package edu.aleksandrTreskov.mms.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {
    private WebDriver browser;
    private final String AUTH_LOGIN = "http://localhost:8088/profile";

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "/Program Files/Google/Chrome/Application/chromedriver.exe");
        browser = new ChromeDriver();
    }


    @Test
    public void loginPositiveSeleniumTest1() {
        browser.get(AUTH_LOGIN);

        browser.findElement(By.id("email")).sendKeys("Abc@gmail.com");
        browser.findElement(By.id("password")).sendKeys("1234");

        browser.findElement(By.id("loginButton")).click();
        String email = browser.findElement(By.id("email")).getText();
        assertEquals(AUTH_LOGIN, browser.getCurrentUrl());
        assertEquals("Abc@gmail.com", email);
    }

    @Test
    public void loginNegativeSeleniumTest2() {
        browser.get(AUTH_LOGIN);

        browser.findElement(By.id("email")).sendKeys("Abc@gmail.com");
        browser.findElement(By.id("password")).sendKeys("12345");

        browser.findElement(By.id("loginButton")).click();

        assertEquals("http://localhost:8088/auth/login?error", browser.getCurrentUrl());
    }

    @After
    public void destroy() {
        browser.close();
    }
}
