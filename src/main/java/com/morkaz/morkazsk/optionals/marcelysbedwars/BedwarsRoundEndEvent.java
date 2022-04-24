package com.morkaz.morkazsk.optionals.marcelysbedwars;

import ch.njol.skript.lang.util.SimpleEvent;
import com.morkaz.morkazsk.managers.RegisterManager;
import de.marcely.bedwars.api.event.RoundEndEvent;

public class BedwarsRoundEndEvent {

	static {
		RegisterManager.registerEvent(
				"Marcelys Bedwars Round End",
				SimpleEvent.class,
				new Class[]{RoundEndEvent.class},
				"[morkazsk] marcelys bedwars round end"
		)
				.description("Called when round ends.")
				.since("1.3");
	}

}
