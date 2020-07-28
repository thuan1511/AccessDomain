package SwitchNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class switchnetwork {
	public void switchToSpecificNetwork(String networkName, String networkPass) {

		String osName = System.getProperty("os.name").toLowerCase();
		String cmd;
		if (osName.contains("mac")) {
			cmd = "networksetup -setairportnetwork en0" + " " + networkName + " " + networkPass;
		} else {
			cmd = "netsh wlan connect ssid=" + networkName + " name=" + networkName;
		}
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			System.out.println("the output stream is " + process.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String s;
			while ((s = reader.readLine()) != null) {
				System.out.println("The inout stream is " + s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
