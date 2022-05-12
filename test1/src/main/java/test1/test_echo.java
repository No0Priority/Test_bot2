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
	static boolean Started;
	public void check_crypto_commands(Update update, String crypto_choice) {
		System.out.println(crypto_choice+" success");

	}
	public void check_crypto_commands(Update update) {
		for (String command: crypto_dict.keySet()) {
			//System.out.println(command);
	    	if (update.getMessage().getText().equals(command)) {
	        	//System.out.println(command+"success");
	        	String crypto_price = crypto_dict.get(command);
	        	//System.out.println(command+": "+crypto_price);
	    		//SendMsg("1341282234", "Ethereum: "+eth);
	    		SendMsg(update.getMessage().getChatId().toString(), command+" "+crypto_price);
			}
		}
	}
	public void create_buttons(String question, ArrayList<String> crypto_list, Update update) {
		//System.out.println("new function success");
		SendMessage sendMessage = new SendMessage();
		sendMessage.setText(question);
		sendMessage.setParseMode(ParseMode.MARKDOWN);
		sendMessage.setChatId(update.getMessage().getChatId().toString());
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		replyKeyboardMarkup.setResizeKeyboard(true);
		List<KeyboardRow> keyboardRowList = new ArrayList<>();
		KeyboardRow keyboardRow1 = new KeyboardRow();
//		keyboardRow1.add("ass");
//		keyboardRow1.add("tits");
//		keyboardRow1.add("Tether");
		//System.out.println(Arrays.asList(crypto_list));
		for (String button : crypto_list) {
			keyboardRow1.add(button);
			//System.out.println(button);
		}
		keyboardRowList.add(keyboardRow1);
		replyKeyboardMarkup.setKeyboard(keyboardRowList);
		sendMessage.setReplyMarkup(replyKeyboardMarkup);
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println("some shit");
		}
		//System.out.println("new function end");
	}
	public void onUpdateReceived(Update update) {
	    if (update.hasMessage() && update.getMessage().hasText()) {
	        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
	        //message.setChatId(update.getMessage().getChatId().toString());
	        //message.setText(update.getMessage().getText());
	    	

	            try {
	            	//SendMsg("1341282234", update.getMessage().getText());
	            	SendMsg(update.getMessage().getChatId().toString(), update.getMessage().getText());
	            	
	            	if (update.getMessage().getText().equals("/start")) {
	            		if (!Started) {
		            		test_echo ts = new test_echo();
		            		Thread thread = new Thread(ts);
		            		thread.start();
		            		Started = true;
	            		}
	            		//System.out.println(Arrays.asList(crypto_dict.keySet()));
	            		//System.out.println(crypto_dict.keySet().isEmpty());
	            		while (crypto_dict.keySet().isEmpty()) {
	            			Thread.sleep(1000);
	            			System.out.println("======");
	            			System.out.println("sleeping");
		            		System.out.println(Arrays.asList(crypto_dict.keySet()));
		            		System.out.println(crypto_dict.keySet().isEmpty());
		            		System.out.println("=======");
	            		}
	            		Thread.sleep(1000);
            			create_buttons("Ass and tits question", new ArrayList<>(crypto_dict.keySet()), update);
    				}
	            	check_crypto_commands(update);

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

			prices = driver.findElements(By.className("js-currency-price"));			

			for (WebElement str:prices) {
				crypto_dict.put(str.getAttribute("title"), str.getText());
			}
			System.out.println("running");
			driver.navigate().refresh();
		}
		
	}

}
