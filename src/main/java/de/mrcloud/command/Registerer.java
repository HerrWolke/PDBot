package de.mrcloud.command;

import de.mrcloud.command.commands.*;
import de.mrcloud.utils.DataStorage;

public class Registerer {
    public Registerer() {
        System.out.println("Registering all Commands");
        DataStorage.registerCommands(
                new SendMessageCommand(),
                new RemoveCommand(),
                new ClearOnDuty(),
                new AddCops(),
                new EditCommand(),
                new ListCop(),
                new RemoveCopCommand(),
                new HelpCommand(),
                new SetCopCommand(),
                new ReloadBotCommand()
        );
    }
}

