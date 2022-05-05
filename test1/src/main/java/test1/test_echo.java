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

public class test_echo extends TelegramLongPollingBot {


	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
	    if (update.hasMessage() && update.getMessage().hasText()) {
	        //SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
	        //message.setChatId(update.getMessage().getChatId().toString());
	        //message.setText(update.getMessage().getText());
	        

	            try {
	            	SendMsg("1341282234", update.getMessage().getText());
	            	System.out.println("before");
	            	System.out.println("|"+update.getMessage().getText()+"|");
	            	System.out.println(String.valueOf(update.getMessage().getText().equals("/test")));
	            	System.out.println("after");
	            	if (update.getMessage().getText().equals("/test")) {
	            		System.out.println("success");
	            	// ===========INITIALIZATION==============
	                //TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);// оставить в мейне
	                //telegramBotsApi.registerBot(new test_echo()); //оставить в мейне
	        		WebDriverManager.firefoxdriver().setup();
	        		WebDriver driver = new FirefoxDriver();
	        		driver.get("https://www.investing.com/crypto/");
	        		test_echo ts = new test_echo(); 
	        		// =========================
	        		
	        		//for (int i = 0; i<100; i++) {
	        			Thread.sleep(5000);
	        			//Elements Alex = doc.getElementsByClass("pid-1057391-last"); asdasd
	        			//System.out.println(Alex.text());
	        			WebElement bitcoin = driver.findElement(By.className("pid-1057391-last"));
	        			List<WebElement> plants = driver.findElements(By.className("js-currency-price"));
	        			System.out.println("before");
	        			Map<String, String> crypto_dict = new HashMap<String, String>();

	        			for (WebElement str:plants) {
	        				crypto_dict.put(str.getAttribute("title"), str.getText());
	        			}
	        			System.out.println(Arrays.asList(crypto_dict));
	        			String bitc = crypto_dict.get("BTC");
	        			String eth = crypto_dict.get("ETH");
	        			System.out.println("ETH"+eth+"BTC"+bitc);

	        			System.out.println("after");
	        			ts.SendMsg("1341282234", "курс битка: "+bitcoin.getText());
	        			
	        			ts.SendMsg("844626806", "курс битка: "+bitcoin.getText());
	        			driver.navigate().refresh();
	        		//}

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

}
