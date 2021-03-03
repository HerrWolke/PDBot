package de.mrcloud.command.commands;

import de.mrcloud.command.Command;
import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ClearOnDuty extends Command {
    public ClearOnDuty() {
        super("clearlist", "Löscht die momentan anzeigte Dienstliste", "!clearlist", Category.STAFF);
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent e, String[] args) {
        Member member = e.getMember();
        TextChannel txtChannel = e.getChannel();
        if (JDAUtils.hasRole(member, "Personalabteilung", true)) {
            DataStorage.onDuty.clearOnDuty();
            JDAUtils.greenBuilder("Erfolg", "Die Dienstliste wurde erfolgreich geleert!", member, txtChannel);
        }

        return false;
    }

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
        return false;
    }
}
