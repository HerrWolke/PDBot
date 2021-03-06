package de.mrcloud.command.commands;

import de.mrcloud.command.Command;
import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import static de.mrcloud.main.Main.server;

public class AddCops extends Command {
    public AddCops() {
        super("addcop", "Fügt einen neuen Cop mit einer neuen Dienstnummer hinzu", Command.PREFIX + "setcop <@Member/MemberID> <Dienstnummer im Format OL [NUMMER]> ", Category.STAFF);
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent e, String[] args) {
        Member member = e.getMember();
        TextChannel txtChannel = e.getChannel();
        if (JDAUtils.hasRole(member, "Personalabteilung", true)) {
            if (e.getMessage().getMentionedMembers().isEmpty()) {
                if (args[0].matches("\\d*"))
                    DataStorage.cops.addMedic(args[0], args[1] + " " + args[2]);
                else
                    JDAUtils.redBuilder("Error", "Bitte benutze die ID des Members oder erwähne ihn!", e.getMember(), e.getChannel());
            } else {
                DataStorage.cops.addMedic(e.getMessage().getMentionedMembers().get(0).getId(), args[1] + " " + args[2]);
            }


            JDAUtils.greenBuilder("Erfolg", "Der Cop " + server.getMemberById(Long.parseLong(args[0].replaceAll("<", "").replaceAll("!", "").replaceAll(">", "").replaceAll("@", ""))).getAsMention() + " mit der Dienstnumemr " + args[1] + " " + args[2] + " wurde erfolgreich hinzugefügt!", member, txtChannel);
        } else {
            JDAUtils.redBuilder("Error","Du hast keine Berechtigung diesen Command zu benutzen. Du benötigst die Rolle " + server.getRolesByName("Personalabteilung",true).get(0).getAsMention() + " !",member,e.getChannel(),20);
        }

        return false;
    }

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
        return false;
    }
}
