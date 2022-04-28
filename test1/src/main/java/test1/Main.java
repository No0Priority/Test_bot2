package test1;
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

    public static void main(String[] args) throws InterruptedException {
    	PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/resources/log4j.properties");

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new test_echo());
    		WebDriverManager.firefoxdriver().setup();
    		WebDriver driver = new FirefoxDriver();
    		driver.get("https://www.investing.com/crypto/");
    		test_echo ts = new test_echo();
    		for (int i = 0; i<100; i++) {
    			Thread.sleep(5000);
    			//Elements Alex = doc.getElementsByClass("pid-1057391-last");
    			//System.out.println(Alex.text());
    			WebElement bitcoin = driver.findElement(By.className("pid-1057391-last"));
    			/*List<WebElement> plants = driver.findElements(By.className());
    			for (WebElement str:plants) {
    				System.out.println(str.getText());
    			}*/
    			
    			ts.SendMsg("1341282234", "курс битка: "+bitcoin.getText());
    			
    			ts.SendMsg("844626806", "курс битка: "+bitcoin.getText());
    			driver.navigate().refresh();
    		}

    		driver.quit();

    	
            /*
            while (true) {
            	Thread.sleep(1);
	    		try {
	    			Document doc = Jsoup.connect("https://wiki.narva.ut.ee/index.php?title=Sssd").get();
	    			Elements Alex = doc.getElementsByClass("mw-parser-output");
	    			String str = Alex.first().text();
	    			if (str.equals("desired output")  || str.equals("Alex") || str.equals("iphone for 100 bucks")) {
		    			System.out.println(str);
		    			test_echo ts = new test_echo();
		    			ts.SendMsg("1341282234", str);
		    			
		    			ts.SendMsg("844626806", str);
		    			break;
	    			}
	    		}
	    		catch (Exception e) {
	    			System.out.println(e);
	    		}
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
