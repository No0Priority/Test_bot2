package test1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
class User {
	String user_id;
	ArrayList<HashMap<String, String>> crypto_choices = new ArrayList<>();
	Map<String, String> dialogue_id = new HashMap<String, String>();
	int dialogue_counter = 0;
	User(String user_id){
		this.user_id = user_id;
	}

}
public class test_echo extends TelegramLongPollingBot implements Runnable{
	// list of users connected to the bot
	static HashMap<String, User> users = new HashMap<String, User>();
	// iteration through dialogue phases
    //static int dialogue_counter = 0;
    // crypto_name : crypto_price
	static Map<String, String> crypto_dict = new HashMap<String, String>();
	// Firefox started?
	static boolean Started;
	// user_choice : value
	//static ArrayList<HashMap<String, String>> crypto_choices = new ArrayList<>();
	// variable to track replies to certain messages
	//static Map<String, String> dialogue_id = new HashMap<String, String>();

	// getting future reply message id
	public void save_future_reply_id(Update update, String command, String dialogue_phase) {
		User user = users.get(update.getMessage().getChatId().toString());
		user.dialogue_id.put("message_id", String.valueOf(update.getMessage().getMessageId()+2));
		user.dialogue_id.put("command", command);
		user.dialogue_id.put("dialogue_phase", dialogue_phase);
		System.out.println("id of choice: "+String.valueOf(update.getMessage().getMessageId()+2));
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

		if (update.hasMessage() && update.getMessage().hasText()) {
			System.out.println("message id: "+update.getMessage().getMessageId().toString()+ "message: || "+ update.getMessage().getText());
			String user_id = update.getMessage().getChatId().toString();
			User user = users.get(user_id);
			System.out.println("user id: "+user_id);
			System.out.println("1: " +update.getUpdateId().toString());
	            try {    	
	            	// ====== start the monitoring tool =======
	            	if (update.getMessage().getText().equals("/start")) {
	            		
	            		//save_future_reply_id(update, "/start", "init");
            			// adding the user to users list
            			if (user == null) {
            				users.put(user_id,new User(user_id));
            			}
            			
            			
	            		// if the firefox hasnt been initiated
	            		if (!Started) {
	            			// starting firefox
		            		test_echo ts = new test_echo();
		            		Thread thread = new Thread(ts);
		            		thread.start();
		            		Started = true;
	            		}
    				}
	            	if (update.getMessage().getText().equals("/clear")) {
	            		user.crypto_choices.clear();
	            	}
	            	
	            	check_crypto_commands(update);
	            	//===== if starting command to add crypto ====
	            	if (update.getMessage().getText().equals("/addcrypto")) {
	            		System.out.println("running add crypto");
	            		// if crypto_dict hasnt been filled yet loop waiting
	            		while (crypto_dict.keySet().isEmpty()) {
	            			Thread.sleep(1000);
	            		}
	            		Thread.sleep(1000);
	            		// create buttons for /addcrypto command
            			create_buttons("Choose crypto", new ArrayList<>(crypto_dict.keySet()), update);
            			// save message id of a reply to crypto choice
            			save_future_reply_id(update, "/addcrypto", "get_name");
	            	}
    				
            		// ====== if a reply is awaited ======
	            	if (!user.dialogue_id.isEmpty()) {
	            		// if current message is awaited
	            		boolean replied = user.dialogue_id.get("message_id").equals(String.valueOf(update.getMessage().getMessageId()));
	            		// iterate through dialogue phases
	            		user.dialogue_counter++;
	            		System.out.println("======");
	            		System.out.println("dialogue"+String.valueOf(user.dialogue_counter));
	            		System.out.println(Arrays.asList(user.dialogue_id));
	            		System.out.println("======");
		            	if (replied) {
		            		System.out.println("cehck: ");
		            		System.out.println(user.dialogue_id.get("command")+ " " +user.dialogue_id.get("dialogue_phase")+ String.valueOf(user.dialogue_counter==1)+String.valueOf(user.dialogue_counter));
		            		// ==addcrypto -> get_name==
		            		if ( user.dialogue_counter==2&& user.dialogue_id.get("command").equals("/addcrypto") && user.dialogue_id.get("dialogue_phase").equals("get_name")) {
		            			System.out.println("running get name");
		            			// add new choice dictionary to the list
		            			user.crypto_choices.add(new HashMap<String, String>());
		            			// remember crypto name choice
		            			user.crypto_choices.get(user.crypto_choices.size()-1).put("crypto_name", update.getMessage().getText().toString());
		            			create_buttons("Choose condition", new ArrayList<>(Arrays.asList("greater", "smaller")), update);
		            			// clear space for the next awaited message
		            			user.dialogue_id.clear();
		            			// remember next message_id and its relevance to the dialogue
		            			save_future_reply_id(update, "/addcrypto", "get_condition");
		            		}
		            		// ==addcrypto -> get_name -> get_condition==
		            		if (user.dialogue_counter==3 && user.dialogue_id.get("command").equals("/addcrypto") && user.dialogue_id.get("dialogue_phase").equals("get_condition")) {
		            			System.out.println("running get condition");
		            			user.crypto_choices.get(user.crypto_choices.size()-1).put("crypto_condition", update.getMessage().getText().toString());
		            			SendMsg(user.user_id, "Enter chosen crypto value: ");
		            			user.dialogue_id.clear();
		            			save_future_reply_id(update, "/addcrypto", "get_value");
		            		}
		            		// ==addcrypto -> get_name -> get_condition -> get_value==
		            		if (user.dialogue_counter==4 && user.dialogue_id.get("command").equals("/addcrypto") && user.dialogue_id.get("dialogue_phase").equals("get_value")) {
		            			System.out.println("running get value and print");
		            			user.crypto_choices.get(user.crypto_choices.size()-1).put("crypto_value", update.getMessage().getText().toString());
		            			user.dialogue_id.clear();
		            			String choice = "You chose to monitor "+user.crypto_choices.get(user.crypto_choices.size()-1).get("crypto_name")+", condition is: "
		            					+ user.crypto_choices.get(user.crypto_choices.size()-1).get("crypto_condition") + " than "+ user.crypto_choices.get(user.crypto_choices.size()-1).get("crypto_value");
		            			SendMsg(user.user_id, choice);
		            			// testing user division
		            			StringBuilder sb = new StringBuilder();
		            			for (HashMap<String, String> map : user.crypto_choices) {
		            				for (String choice_str : map.values()) {
		            					sb.append("|"+choice_str+"|");
		            				}
		            			}
		            			SendMsg(user.user_id, sb.toString());


		            			user.dialogue_counter = 0;
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
	
	public void choices_to_gate_to_msg() {
		//System.out.println("enter 1");
		for (String test: users.keySet()) {
			//System.out.println(test+"test");
		}
		for (User user: users.values()) {
			//System.out.println("user id "+user.user_id);
			//System.out.println("enter 2");
			for (HashMap<String, String> choices_dict : user.crypto_choices) {
				if (choices_dict.get("crypto_value")!=null && choices_dict.get("crypto_name")!=null) {
					
				
					//System.out.println("enter 3");
					String chosen_crypto_price_string = crypto_dict.get(choices_dict.get("crypto_name")); 
					String trigger = choices_dict.get("crypto_value");
					System.out.println("Trigger: "+trigger+"Real time price: "+ chosen_crypto_price_string);
					float chosen_crypto_price = Float.parseFloat(chosen_crypto_price_string);
					float trigger_crypto_price = Float.parseFloat(choices_dict.get("crypto_value"));
					//System.out.println("real price"+chosen_crypto_price_string+" trigger price: "+ String.valueOf(trigger_crypto_price));
					//System.out.println("smaller: "+ String.valueOf(chosen_crypto_price<trigger_crypto_price)+ " bigger: "+ String.valueOf(chosen_crypto_price>trigger_crypto_price));
					//System.out.println("condition: "+ choices_dict.get("crypto_condition"));
					String crypto_success_msg = choices_dict.get("crypto_name")+" is now "+choices_dict.get("crypto_condition")+
							" than "+choices_dict.get("crypto_value")+", real-time "+choices_dict.get("crypto_name")+" value: "+chosen_crypto_price_string;
					if (choices_dict.get("crypto_condition").equals("smaller")&&chosen_crypto_price<trigger_crypto_price) {
						SendMsg(user.user_id, crypto_success_msg);
					}
					else if (choices_dict.get("crypto_condition").equals("greater")&&chosen_crypto_price>trigger_crypto_price) {
						SendMsg(user.user_id, crypto_success_msg);
					}
				}
				else {
					continue;
				}
			}
		}
	}
	public void run() {
		try {
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
			List<WebElement> crypto_prices = driver.findElements(By.className("js-currency-price"));	

			for (WebElement str:crypto_prices) {
				crypto_dict.put(str.getAttribute("title"), str.getText().replace(",", ""));
			}
			System.out.println("running");
			choices_to_gate_to_msg();
			driver.navigate().refresh();
		}
		}
		catch (Exception e) {
			System.out.println(" \n \n \n Error: \n \n \n "+e);
		}
		
	}

}
