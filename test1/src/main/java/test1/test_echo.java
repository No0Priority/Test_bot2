package test1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.telegram.telegrambots.meta.TelegramBotsApi;

public class test_echo extends TelegramLongPollingBot implements Runnable{

	static List<WebElement> prices;
	static Map<String, String> crypto_dict = new HashMap<String, String>();
	public void onUpdateReceived(Update update) {
	    if (update.hasMessage() && update.getMessage().hasText()) {
	        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
	        message.setChatId(update.getMessage().getChatId().toString());
	        message.setText(update.getMessage().getText());
	    	

	            try {
	            	//SendMsg("1341282234", update.getMessage().getText());
	            	SendMsg("844626806", update.getMessage().getText());
	            	
	            	if (update.getMessage().getText().equals("/start")) {
	            		SendMessage sendMessage = new SendMessage();
	            		sendMessage.setText("Hello, which cryptocurrency would you like to track?");
	            		sendMessage.setParseMode(ParseMode.MARKDOWN);
	            		sendMessage.setChatId(message.getChatId());
	            		test_echo ts = new test_echo();
	            		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
	            		replyKeyboardMarkup.setResizeKeyboard(true);
	            		List<KeyboardRow> keyboardRowList = new ArrayList<>();
	            		KeyboardRow keyboardRow1 = new KeyboardRow();
	            		keyboardRow1.add("Bitcoin");
	            		keyboardRow1.add("Ethereum");
	            		keyboardRow1.add("Tether");
	            		keyboardRowList.add(keyboardRow1);
	            		replyKeyboardMarkup.setKeyboard(keyboardRowList);
	            		sendMessage.setReplyMarkup(replyKeyboardMarkup);
	            		execute(sendMessage);
	            		Thread thread = new Thread(ts);
	            		thread.start();
    				
    				}
    				
	            	else if (update.getMessage().getText().equals("Bitcoin")) {
	            		System.out.println("bitc success");
	            		String bitc = crypto_dict.get("BTC");
	            		System.out.println(bitc);
	        			//SendMsg("1341282234", "Bitcoin: "+bitc);
	        			
	        			SendMsg("844626806", "Bitcoin: "+bitc);
	            	}
	        			
	            	else if (update.getMessage().getText().equals("Ethereum")) {
		            	System.out.println("ethereum success");
		            	String eth = crypto_dict.get("ETH");
		            	System.out.println(eth);
		        		//SendMsg("1341282234", "Ethereum: "+eth);
		        		SendMsg("844626806", "Ethereum: "+eth);
	        		}
		        
	            	else if (update.getMessage().getText().equals("Tether")) {
		            	System.out.println("tether success");
		            	String usdt = crypto_dict.get("USDT");
		            	System.out.println(usdt);
		        		//SendMsg("1341282234", "Tether: "+usdt);
		        			
		        		SendMsg("844626806", "Tether: "+usdt);
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
