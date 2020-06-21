package util;

import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

	public void write(String message) {
		try {
			message = message.replaceAll("<br/>", "\r\n");
			FileWriter writer = new FileWriter("E:/message.txt");
			writer.write(message);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
