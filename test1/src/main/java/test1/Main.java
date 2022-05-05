package test1;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramWebhookBot;

public class Main {
	// ====Alexey space====
	
	
	
	
	// =====================
    // change 14:26
    public static void main(String[] args) throws InterruptedException {
    	PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/resources/log4j.properties"); //оставить в мейне

        try {
        	// ===========INITIALIZATION==============
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);// оставить в мейне
            telegramBotsApi.registerBot(new test_echo()); //оставить в мейне
            System.out.println("start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
