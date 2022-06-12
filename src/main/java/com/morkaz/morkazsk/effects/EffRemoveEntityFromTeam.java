package com.morkaz.morkazsk.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.morkaz.morkazsk.managers.RegisterManager;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@Name("Remove Entity From Team")
@Description({ "Allows to remove an entity from a team" })
@Examples({ "remove player from team \"Queso\"" })
@Since("1.3.1")
public class EffRemoveEntityFromTeam
        extends Effect {
    Expression<Entity> entityExpr;
    Expression<String> teamNameExpr;

    static {
        RegisterManager.registerEffect(EffRemoveEntityFromTeam.class,
                "[morkazsk] remove %entity% from team %string%");
    }

    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed,
            SkriptParser.ParseResult parseResult) {
        this.entityExpr = (Expression<Entity>) exprs[0];
        this.teamNameExpr = (Expression<String>) exprs[1];
        return true;
    }

    public String toString(@Nullable Event e, boolean debug) {
        return String.format("morkazsk remove %s from team %s",
                ((Entity) this.entityExpr.getSingle(e)).getName(),
                this.teamNameExpr.getSingle(e));
    }

    protected void execute(Event e) {
        Entity entity = (Entity) this.entityExpr.getSingle(e);
        String teamName = (String) this.teamNameExpr.getSingle(e);
        if (entity == null || teamName == null) {
            return;
        }

        Scoreboard mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = mainScoreboard.getTeam(teamName);
        if (team == null) {
            return;
        }

        String entityId = (entity instanceof org.bukkit.entity.Player) ? entity.getName()
                : entity.getUniqueId().toString();
        team.removeEntry(entityId);
    }
}
