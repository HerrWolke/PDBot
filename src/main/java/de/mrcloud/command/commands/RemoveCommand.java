package de.mrcloud.command.commands;

import de.mrcloud.command.Command;
import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import static de.mrcloud.main.Main.server;

public class RemoveCommand extends Command {
    public RemoveCommand() {
        super("remove", "Entfernt den gwählten Nutzer aus der Dienstliste", Command.PREFIX + "remove <@Member/Member Name/Member ID>", Category.STAFF);
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent e, String[] args) {
        Member member = e.getMember();
        Member target = null;

        if (e.getMessage().getMentionedMembers().size() > 0)
            target = e.getMessage().getMentionedMembers().get(0);

        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append(arg);
        }

        if (JDAUtils.hasRole(member, "Personalabteilung", true)) {
            if (target != null && DataStorage.onDuty.getListOfOnDuty().contains(target.getIdLong())) {
                DataStorage.onDuty.removeOnDuty(member);
                JDAUtils.greenBuilder("Erfolg", "Die Person wurde Erfolgreich aus der Dienstliste entfernt!", member, e.getChannel());
            } else if (!server.getMembersByNickname(builder.toString(), true).isEmpty()) {
                target = server.getMembersByNickname(builder.toString(), true).get(0);
                if (DataStorage.onDuty.getListOfOnDuty().contains(target.getIdLong())) {
                    DataStorage.onDuty.removeOnDuty(member);
                }
                JDAUtils.greenBuilder("Erfolg", "Die Person wurde Erfolgreich aus der Dienstliste entfernt!", member, e.getChannel());
            } else {
                JDAUtils.redBuilder("Fehler", "Konnte diese Person nicht finden. Bitte benutze ihren Namen auf dem Discord oder erwähne ihn!", member, e.getChannel());
            }
        }  else {
            JDAUtils.redBuilder("Error","Du hast keine Berechtigung diesen Command zu benutzen. Du benötigst die Rolle " + server.getRolesByName("Personalabteilung",true).get(0).getAsMention() + " !",member,e.getChannel(),20);
        }

        e.getMessage().delete().queue();
        return false;
    }

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
        return false;
    }
}
