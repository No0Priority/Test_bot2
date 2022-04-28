package test1;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;

public class test_echo extends TelegramLongPollingBot {


	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
	    if (update.hasMessage() && update.getMessage().hasText()) {
	        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
	        message.setChatId(update.getMessage().getChatId().toString());
	        message.setText(update.getMessage().getText());
	        
	        try {
	            execute(message); // Call method to send the message
	        } catch (TelegramApiException e) {
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
