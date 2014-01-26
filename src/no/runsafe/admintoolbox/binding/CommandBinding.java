package no.runsafe.admintoolbox.binding;

import no.runsafe.framework.api.player.IPlayer;

import java.util.Arrays;
import java.util.List;

public class CommandBinding
{
	public CommandBinding(String command)
	{
		this.commands = Arrays.asList(command.split("\\\\"));
	}

	public CommandBinding(String... commands)
	{
		this.commands = Arrays.asList(commands);
	}

	public void execute(IPlayer player)
	{
		for (String command : commands)
			player.performCommand(command);
	}

	private final List<String> commands;
}
