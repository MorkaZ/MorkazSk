package com.morkaz.morkazsk.expressions.universal;


import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.MorkazSk;
import com.morkaz.morkazsk.managers.RegisterManager;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

@Name("All Lines Of File")
@Description({
		"Return all lines of specific file.",
		"Use %morkazskdir% to get path to plugins/MorkazSk directory."
})
@Examples({
		"all file lines of \"%morkazskdir%/myfile.txt\""
})
@Since("1.2-beta3")

public class ExprLinesOfFile extends SimpleExpression<String> {

	static {
		RegisterManager.registerExpression(
				ExprLinesOfFile.class,
				String.class,
				ExpressionType.SIMPLE,
				"[morkazsk] [all] file lines of %string%"
		);
	}

	Expression<String> filePathExpr;

	public boolean isSingle() {
		return false;
	}

	public String toString(Event event, boolean debug) {
		return "all file lines of " + filePathExpr.toString(event, debug);
	}

	public Class<? extends String> getReturnType() {
		return String.class;
	}

	public boolean init(Expression<?>[] expressions, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		this.filePathExpr = (Expression<String>)expressions[0];
		return true;
	}

	protected String[] get(Event event) {
		String path = filePathExpr.getSingle(event);
		if (path != null){
			path = path.replace("%morkazskdir%", MorkazSk.getInstance().getDataFolder().getPath());
			try {
				List<String> lines = Files.readAllLines(new File(path).toPath(), Charset.defaultCharset());
				return lines.toArray(new String[lines.size()]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new String[]{};
		}
		return new String[]{};
	}

}
