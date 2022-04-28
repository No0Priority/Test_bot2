package snippet;

public class Snippet {
	public static void main(String[] args) {
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

