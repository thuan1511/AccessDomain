package telegrambots;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.util.HttpURLConnection;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;

public class TelegramBot {
	
	private final String TELEGRAM_BOT_TOKEN = "898848790:AAEJNDrQwI4tot6Y5v3Gg0VKXBytDN2pDrY";
    private final String TELEGRAM_CHAT_ID = "-416624316";
    private long telegramChatId;
    private TelegramBot telegramBot;

    public void onStart() {
        telegramBot = new TelegramBot();
    }



	public void notify11(String message) {
    	SendMessage message1 = new SendMessage(); // Create a message object object
        message1.setChatId(TELEGRAM_CHAT_ID)
        .setText("message");
    }

}
