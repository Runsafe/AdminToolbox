package no.runsafe.admintoolbox.commands;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.EnumArgument;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IAmbiguousPlayer;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.player.GameMode;

import java.util.Map;

public class Mode extends ExecutableCommand
{
	public Mode(IServer server, IConsole console)
	{
		super(
				"mode", "Changes the game-mode of the player", "runsafe.toybox.mode",
				new EnumArgument("mode", GameMode.values(), true), new PlayerArgument(false)
		);
		this.server = server;
		this.console = console;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, Map<String, String> parameters)
	{
		if (!(executor instanceof IPlayer) && !parameters.containsKey("player"))
			return "&cYou need to supply a player for this command when called from the console.";

		IPlayer target = (parameters.containsKey("player") ? server.getPlayer(parameters.get("player")) : (IPlayer) executor);
		if (target == null)
			return "Player not found";
		if (target instanceof IAmbiguousPlayer)
			return target.toString();

		if (!target.isOnline())
			return String.format("&cThe player %s is offline.", target.getName());

		GameMode mode = GameMode.search(parameters.get("mode"));
		if (mode == null)
			return String.format("&c%s is not a recognized game mode!", parameters.get("mode"));
		mode.apply(target);
		String feedback = this.getGameModeUpdateMessage(target);
		console.logInformation(feedback);
		return feedback;
	}

	private String getGameModeUpdateMessage(IPlayer target)
	{
		return String.format("%s now has the game mode %s.", target.getPrettyName(), target.getGameMode());
	}

	private final IServer server;
	private final IConsole console;
}