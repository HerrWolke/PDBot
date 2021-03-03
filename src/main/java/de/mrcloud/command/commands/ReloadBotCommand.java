package de.mrcloud.command.commands;

import de.mrcloud.command.Command;
import de.mrcloud.utils.DataStorage;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ReloadBotCommand extends Command {
    public ReloadBotCommand() {
        super("reload", "Läd alle Cops und Settings neu", "!reload", Category.STAFF);
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent e, String[] args) {
        DataStorage.loadSettings();
        DataStorage.loadCops();

        return false;
    }

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
        return false;
    }
}
