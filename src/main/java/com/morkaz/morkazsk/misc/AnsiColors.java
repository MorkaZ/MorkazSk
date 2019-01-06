package com.morkaz.morkazsk.misc;

import org.fusesource.jansi.Ansi;

public class AnsiColors {

	public static final String BLUE = Ansi.ansi().fg(Ansi.Color.BLUE).boldOff().toString();
	public static final String RED = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString();
	public static final String GREEN = Ansi.ansi().fg(Ansi.Color.GREEN).boldOff().toString();
	public static final String CYAN = Ansi.ansi().fg(Ansi.Color.CYAN).boldOff().toString();
	public static final String MAGENTA = Ansi.ansi().fg(Ansi.Color.MAGENTA).boldOff().toString();
	public static final String YELLOW = Ansi.ansi().fg(Ansi.Color.YELLOW).boldOff().toString();
	public static final String BLACK = Ansi.ansi().fg(Ansi.Color.BLACK).boldOff().toString();
	public static final String WHITE = Ansi.ansi().fg(Ansi.Color.WHITE).boldOff().toString();

	public static String translate(String prefix, String text){
		return text
				.replace(prefix+"2", GREEN)
				.replace(prefix+"1", BLUE)
				.replace(prefix+"3", CYAN)
				.replace(prefix+"4", RED)
				.replace(prefix+"5", MAGENTA)
				.replace(prefix+"6", YELLOW)
				.replace(prefix+"7", WHITE)
				.replace(prefix+"8", WHITE)
				.replace(prefix+"9", CYAN)
				.replace(prefix+"0", BLACK)
				.replace(prefix+"a", GREEN)
				.replace(prefix+"b", BLUE)
				.replace(prefix+"c", RED)
				.replace(prefix+"d", MAGENTA)
				.replace(prefix+"f", WHITE)
				.replace(prefix+"e", YELLOW)
		;
	}

}
