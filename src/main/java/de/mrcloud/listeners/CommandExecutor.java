package de.mrcloud.listeners;

import de.mrcloud.command.Command;
import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class CommandExecutor extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        super.onGuildMessageReceived(e);

        if (e.getMessage().getContentRaw().equals("!setAll")) {
            for (Member member : e.getGuild().getMembers()) {
                if (JDAUtils.hasRole(member, "Los Santos Police Department", true)) {
                    System.out.println(member.getNickname());
                    if (member.getNickname().equals("[PD - A. Chief | OL - 3] Ambrose")) {
                        try {
                            DataStorage.cops.addMedic(member.getId(), "OL 3");

                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }

                    } else if (member.getNickname().contains("-")) {
                        DataStorage.cops.addMedic(member.getId(), member.getNickname().split(" ")[0].replace("[", "").replace("]", " ").replaceAll(" \\|", "").replace("-", " ").replace("Francesco JavierValentin", ""));
                    } else
                        DataStorage.cops.addMedic(member.getId(), member.getNickname().split(" ")[0].replace("[", "").replace("]", "") + " " + member.getNickname().split(" ")[1].replace("[", "").replace("]", ""));
                }
            }
        }

        if (!e.getMessage().getContentRaw().startsWith(Command.PREFIX)) return;
        String[] splitContent = e.getMessage().getContentRaw().split("\\s++");
        String command = splitContent[0];
        if (command.startsWith(Command.PREFIX)) {
            String[] args = new String[splitContent.length - 1];
            for (int i = 0; i < splitContent.length; i++) {
                if (i > 0) args[i - 1] = splitContent[i];
            }

            for (Command cmd : DataStorage.commands) {
                if ((Command.PREFIX + cmd.name).equalsIgnoreCase(command)) {
                    cmd.execute(e, args);
                    break;
                }


            }
        }
    }
}

