package de.mrcloud.command.commands;

import de.mrcloud.command.Command;
import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import static de.mrcloud.main.Main.server;

public class SetCopCommand extends Command {
    public SetCopCommand() {
        super("setcop", "Setzt einen Cop in den gewählten Dienstlistenslot", Command.PREFIX + "setcop <@Member/MemberID> <Dienstnummer im Format OL[NUMMER]> ", Category.STAFF);
    }


    @Override
    public boolean execute(GuildMessageReceivedEvent e, String[] args) {

        boolean success = false;
        Member member = e.getMember();
        TextChannel txtChannel = e.getChannel();
        if (JDAUtils.hasRole(member, "Personalabteilung", true)) {
            if (e.getMessage().getMentionedMembers().isEmpty()) {
                if (args[0].matches("\\d*"))
                    success = DataStorage.cops.setCop(args[0], args[1] + " " + args[2]);
                else
                    JDAUtils.redBuilder("Error", "Bitte benutze die ID des Members oder erwähne ihn!", e.getMember(), e.getChannel());
            } else {
                success = DataStorage.cops.setCop(e.getMessage().getMentionedMembers().get(0).getId(), args[1] + " " + args[2]);
            }

            if (success)
                JDAUtils.greenBuilder("Erfolg", "Der Cop " + server.getMemberById(Long.parseLong(args[0].replaceAll("<", "").replaceAll("!", "").replaceAll(">", "").replaceAll("@", ""))).getAsMention() + " mit der Dienstnumemr " + args[1] + " " + args[2] + " wurde erfolgreich in die Dienstliste hinzugefügt!!", member, txtChannel);
            else
                JDAUtils.redBuilder("Error", "Es ist ein Fehler aufgetreten. Bitte überprüfe deine Eingaben", e.getAuthor(), e.getChannel());
        }

        return false;
    }

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
        return false;
    }
}

