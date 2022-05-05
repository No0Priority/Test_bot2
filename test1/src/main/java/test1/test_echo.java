package test1;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class test_echo extends TelegramLongPollingBot implements Runnable{

	static List<WebElement> prices;
	static Map<String, String> crypto_dict = new HashMap<String, String>();
	public void onUpdateReceived(Update update) {
	    if (update.hasMessage() && update.getMessage().hasText()) {
	        //SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
	        //message.setChatId(update.getMessage().getChatId().toString());
	        //message.setText(update.getMessage().getText());
	    	

	            try {
	            	SendMsg("1341282234", update.getMessage().getText());
	            	//SendMsg("844626806", update.getMessage().getText());
	            	
	            	if (update.getMessage().getText().equals("/start")) {
	            		test_echo ts = new test_echo();
	            		System.out.println("success");
	            		Thread thread = new Thread(ts);
	            		thread.start();
    				
    				}
    				
	            	if (update.getMessage().getText().equals("/bitc")) {
	            		System.out.println("bitc success");
	            		String bitc = crypto_dict.get("BTC");
	            		System.out.println(bitc);
	        			SendMsg("1341282234", "курс битка: "+bitc);
	        			
	        			//SendMsg("844626806", "курс битка: "+bitc);
	            	}
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	    }
	}
	public void SendMsg(String chat_id, String text) {
		SendMessage message = new SendMessage(); 
		message.setChatId(chat_id);
		message.setText(text);
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}

	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "test_some_shit_bot";
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "5394841904:AAEck1N7QG4kfKhW50_42MjWtjx-2jkex5U";
	}
	public void run() {
		WebDriverManager.firefoxdriver().setup();
		WebDriver driver = new FirefoxDriver();
		driver.get("https://www.investing.com/crypto/");
		
		for (int i = 0; i<100; i++) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			WebElement bitcoin = driver.findElement(By.className("pid-1057391-last"));
			prices = driver.findElements(By.className("js-currency-price"));
			System.out.println("before");
			

			for (WebElement str:prices) {
				crypto_dict.put(str.getAttribute("title"), str.getText());
			}
			System.out.println(Arrays.asList(crypto_dict));
			String bitc = crypto_dict.get("BTC");
			String eth = crypto_dict.get("ETH");
			System.out.println("ETH"+eth+"BTC"+bitc);
			driver.navigate().refresh();
		}
		
	}

}
