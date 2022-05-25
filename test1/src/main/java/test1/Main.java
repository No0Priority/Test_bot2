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
	/* ====Alexey space====
	if (message_text.equals("/menu")) {
		SendMessage message = new SendMessage() // Create a message object object
		.setChatId(chat_id)
		.setText("Your choice");
		// Create ReplyKeyboardMarkup object
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		// Create the keyboard (list of keyboard rows)
		List<KeyboardRow> keyboard = new ArrayList<>();
		// Create a keyboard row
		KeyboardRow row = new KeyboardRow();
		// Set each button, you can also use KeyboardButton objects if you need something else than text
		row.add("Bitcoin");
		row.add("Ethereum");
		row.add("Dogecoin");
		// Add the first row to the keyboard
		keyboard.add(row);
		// Set the keyboard to the markup
		keyboardMarkup.setKeyboard(keyboard);
		// Add it to the message
		message.setReplyMarkup(keyboardMarkup);
		try {
		sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
		e.printStackTrace();
		else if (message_text.equals("Bitcoin")) {
		for (int i = 0; i<100; i++) {
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
		System.out.println("BTC"+bitc);

		System.out.println("after");
		ts.SendMsg("1341282234", "���� �����: "+bitcoin.getText());

		ts.SendMsg("844626806", "���� �����: "+bitcoin.getText());
		driver.navigate().refresh();

		} catch (TelegramApiException e) {
		e.printStackTrace();
		else if (message_text.equals("Ethereum")) {
		for (int i = 0; i<100; i++) {
		Thread.sleep(5000);
		String eth = crypto_dict.get("ETH");
		System.out.println("ETH"+eth);

		else if (message_text.equals("Tether")) {
		for (int i = 0; i<100; i++) {
		Thread.sleep(5000);
		String eth = crypto_dict.get("USDT");
		System.out.println("USDT"+usdt); asdddddddddddddddddddddddddddddddddddddd
	*/
	
	
	// =====================
    // change 14:26
    public static void main(String[] args) throws InterruptedException {
    	PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/resources/log4j.properties"); //оставить в мейне
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\nidas\\Downloads\\chromedriver_win32\\chromedriver.exe");

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
