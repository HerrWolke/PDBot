package de.mrcloud.command.commands;

import de.mrcloud.command.Command;
import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import static de.mrcloud.main.Main.server;

public class EditCommand extends Command {
    public EditCommand() {
        super("editcop", "Ändert die Dienstnummer eines Cops", "", Category.STAFF);
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent e, String[] args) {
        Member member = e.getMember();
        TextChannel txtChannel = e.getChannel();
        if (JDAUtils.hasRole(member, "Personalabteilung", true)) {
            if (e.getMessage().getMentionedMembers().isEmpty())
                DataStorage.cops.editMedic(args[0], args[1] + " " + args[2]);
            else
                DataStorage.cops.editMedic(e.getMessage().getMentionedMembers().get(0).getId(), args[1] + " " + args[2]);
            JDAUtils.greenBuilder("Erfolg", "Der Cop " + server.getMemberById(Long.parseLong(args[0])).getAsMention() + " mit der Dienstnumemr " + args[1] + " " + args[2] + " wurde erfolgreich bearbeitet!", member, txtChannel);
        }

        return false;
    }

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
        return false;
    }
}
