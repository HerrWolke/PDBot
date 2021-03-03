package de.mrcloud.command.commands;

import de.mrcloud.command.Command;
import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Map;

import static de.mrcloud.main.Main.server;

public class RemoveCopCommand extends Command {
    public RemoveCopCommand() {
        super("removecop", "", "", Category.STAFF);
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent e, String[] args) {
        Map.Entry<String, String> returned;
        Member member = e.getMember();
        TextChannel txtChannel = e.getChannel();
        String dienstnummer = "";
        if (JDAUtils.hasRole(member, "Personalabteilung", true)) {
            if (e.getMessage().getMentionedMembers().isEmpty()) {

                if (args.length > 1) {
                    returned = DataStorage.cops.removeMedic(args[0] + " " + args[1]);
                } else {
                    if (e.getMessage().getMentionedMembers().size() > 0)
                        returned = DataStorage.cops.removeMedic(e.getMessage().getMentionedMembers().get(0).getId());
                    else {
                        returned = DataStorage.cops.removeMedic(args[0]);
                    }
                }
                if (returned == null) {
                    JDAUtils.redBuilder("Error", "Der Cop konnte nicht gefunden werden.", e.getMember(), txtChannel);
                    return false;
                }

                dienstnummer = returned.getValue();

                JDAUtils.greenBuilder("Erfolg", "Der Cop " + server.getMemberById(returned.getKey()).getAsMention() + " mit der Dienstnummer " + "OL " + dienstnummer + " wurde erfolgreich entfernt!", member, txtChannel);
            } else {
                DataStorage.cops.removeMedic(e.getMessage().getMentionedMembers().get(0).getId());
                JDAUtils.greenBuilder("Erfolg", "Der Cop " + e.getMessage().getMentionedMembers().get(0).getAsMention() + " mit der Dienstnummer " + "OL " + dienstnummer + " wurde erfolgreich entfernt!", member, txtChannel);
            }

        }

        return false;
    }

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
        return false;
    }
}
