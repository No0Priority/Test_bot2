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
    static boolean first_time = true;
    static int dialogue_counter = 0;
	static Map<String, String> crypto_dict = new HashMap<String, String>();
	static boolean Started;
	static ArrayList<HashMap<String, String>> choices = new ArrayList<>(3);
	// variable to track replies to certain messages
	static ArrayList<String> dialogue_id = new ArrayList<String>();
	// getting future reply message id
	public void save_future_reply_id(Update update, String command, String dialogue_step) {

		dialogue_id.add(String.valueOf(update.getMessage().getMessageId()+2));
		dialogue_id.add(command);
		dialogue_id.add(dialogue_step);
		System.out.println("id of choice: "+update.getMessage().getMessageId().toString()+2);
	}
	// iterate through the list of crypto, get value of crypto in comand
	public void check_crypto_commands(Update update) {
		System.out.println("running check");
		for (String command: crypto_dict.keySet()) {
			//System.out.println(command);
	    	if (update.getMessage().getText().equals("/"+command)) {
	        	//System.out.println(command+"success");
	        	String crypto_price = crypto_dict.get(command);
	        	System.out.println(command+": "+crypto_price);
	    		//SendMsg("1341282234", "Ethereum: "+eth);
	    		SendMsg(update.getMessage().getChatId().toString(), command+" "+crypto_price);
			}
		}
	}
	// Send question (String question), create buttons with names (crypto_list)
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
		System.out.println("message id: "+update.getMessage().getMessageId().toString());

	    if (update.hasMessage() && update.getMessage().hasText()) {
	            try {    	
	            	// start the monitoring tool
	            	if (update.getMessage().getText().equals("/start")) {
	            		//save_future_reply_id(update, "/start", "init");
	            		
	            		if (!Started) {
		            		test_echo ts = new test_echo();
		            		Thread thread = new Thread(ts);
		            		thread.start();
		            		Started = true;
	            		}
    				}
	            	check_crypto_commands(update);
	            	if (update.getMessage().getText().equals("/addcrypto")) {
	            		System.out.println("running add crypto");
	            		while (crypto_dict.keySet().isEmpty()) {
	            			Thread.sleep(1000);
	            		}
	            		Thread.sleep(1000);
            			create_buttons("Choose crypto", new ArrayList<>(crypto_dict.keySet()), update);
            			save_future_reply_id(update, "/addcrypto", "get_name");
	            	}
    				
            		
	            	if (!dialogue_id.isEmpty()) {
	            		boolean replied = dialogue_id.get(0).equals(String.valueOf(update.getMessage().getMessageId()));
	            		dialogue_counter++;
	            		System.out.println("======");
	            		System.out.println("dialogue"+String.valueOf(dialogue_counter));
	            		System.out.println(Arrays.asList(dialogue_id));
	            		System.out.println("======");
		            	if (replied) {
		            		System.out.println("cehck: ");
		            		System.out.println(dialogue_id.get(1)+ " " +dialogue_id.get(2)+ String.valueOf(dialogue_counter==1)+String.valueOf(dialogue_counter));
		            		if ( dialogue_counter==2&& dialogue_id.get(1).equals("/addcrypto") && dialogue_id.get(2).equals("get_name")) {
		            			System.out.println("running get name");
		            			choices.add(new HashMap<String, String>());
		            			choices.get(choices.size()-1).put("crypto_name", update.getMessage().getText().toString());
		            			create_buttons("Choose condition", new ArrayList<>(Arrays.asList("greater", "smaller")), update);
		            			dialogue_id.clear();
		            			save_future_reply_id(update, "/addcrypto", "get_condition");
		            		}
		            		replied = dialogue_id.get(0).equals(String.valueOf(update.getMessage().getMessageId()));
		            		if (dialogue_counter==3 && dialogue_id.get(1).equals("/addcrypto") && dialogue_id.get(2).equals("get_condition")) {
		            			System.out.println("running get condition");
		            			choices.get(choices.size()-1).put("crypto_condition", update.getMessage().getText().toString());
		            			SendMsg(update.getMessage().getChatId().toString(), "Enter chosen crypto value: ");
		            			dialogue_id.clear();
		            			save_future_reply_id(update, "/addcrypto", "get_value");
		            		}
		            		replied = dialogue_id.get(0).equals(String.valueOf(update.getMessage().getMessageId()));
		            		if (dialogue_counter==4 && dialogue_id.get(1).equals("/addcrypto") && dialogue_id.get(2).equals("get_value")) {
		            			System.out.println("running get value and print");
		            			choices.get(choices.size()-1).put("crypto_value", update.getMessage().getText().toString());
		            			dialogue_id.clear();
		            			String choice = "You chose to monitor"+choices.get(choices.size()-1).get("crypto_name")+", condition is: "
		            					+ choices.get(choices.size()-1).get("crypto_condition") + "than "+ choices.get(choices.size()-1).get("crypto_value");
		            			SendMsg(update.getMessage().getChatId().toString(), choice);
		            		}

		            		
		            		
		            	}
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

			prices = driver.findElements(By.className("js-currency-price"));			

			for (WebElement str:prices) {
				crypto_dict.put(str.getAttribute("title"), str.getText());
			}
			System.out.println("running");
			driver.navigate().refresh();
		}
		
	}

}
