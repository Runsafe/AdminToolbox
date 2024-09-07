package no.runsafe.admintoolbox.commands;

import no.runsafe.admintoolbox.Config;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.Enumeration;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.Player;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.player.GameMode;

public class Mode extends ExecutableCommand
{
	public Mode(IConsole console)
	{
		super(
			"mode",
			"Changes the game-mode of the player",
			"runsafe.toybox.mode",
			new Enumeration("mode", GameMode.values()).require(),
			new Player().onlineOnly().defaultToExecutor()
		);
		this.console = console;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		IPlayer target = parameters.getValue("player");
		if (target == null)
		{
			if (!(executor instanceof IPlayer))
				return Config.Message.mode.failConsole;
			else
				target = (IPlayer) executor;
		}
		GameMode mode = parameters.getValue("mode");
		if (mode == null)
			return null;
		mode.apply(target);
		String feedback = this.getGameModeUpdateMessage(target);
		console.logInformation(feedback);
		return feedback;
	}

	private String getGameModeUpdateMessage(IPlayer target)
	{
		return String.format(Config.Message.mode.succeed, target.getPrettyName(), target.getGameMode());
	}

	private final IConsole console;
}
