package telegrambots;


import org.openqa.selenium.WebDriver;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class notifyBot extends TelegramLongPollingBot {

	WebDriver driver;
	private static final String CHAT_ID = "-416624316";
	
	
	@Override
    public void onUpdateReceived(Update update) {

    }

    /**
     * Method for creating a message and sending it.
     * @param chatId chat id
     * @param s The String that you want to send as a message.
     */
    public void sendMsg(String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(CHAT_ID);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

	
	@Override
	public String getBotUsername() {
         return "notifyBot";
     }

	@Override
     public String getBotToken() {
         return "898848790:AAEJNDrQwI4tot6Y5v3Gg0VKXBytDN2pDrY";
     }




	

	

}
