package net.vodculen.drogenmissbrauch.util;

public class Ticks {
	public static int getSeconds(int seconds) {
		return seconds * 20;
	}

	public static int getMinutes(int minutes) {
		return getSeconds(minutes) * 60;
	}

	public static int getHours(int hours) {
		return getMinutes(hours) * 60;
	}
}
