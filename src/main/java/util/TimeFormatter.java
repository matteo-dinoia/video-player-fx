package util;

import javafx.util.Duration;

public class TimeFormatter {

	public static String formatDuration(double seconds) {
		int sec, min, ore;

		sec = (int) seconds;
		min = sec / 60;
		sec %= 60;
		ore = min / 60;
		min %= 60;

		String formatted;
		if(ore != 0) formatted = ore + ":" + twoDigit(min) + ":" + twoDigit(sec);
		else if(min != 0) formatted = min + ":" + twoDigit(sec);
		else formatted = "" + sec;

		return formatted;
	}

	public static String twoDigit(int i){
		if(i < 10)
			return "0" + i;
		return "" + i;
	}
}
