package de.mrcloud.command.commands;

import de.mrcloud.command.Command;
import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

import static de.mrcloud.main.Main.server;

public class KickAndRename extends Command {
    public KickAndRename() {
        super("kickrename", "Removes the persons rank and resets their names", "!kickrename", Category.STAFF);
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent e, String[] args) {
        Member member = e.getMember();
        Guild server = e.getGuild();
        Message message = e.getMessage();
        String messageContent = message.getContentRaw();

        if (JDAUtils.hasRole(member, "Personalabteilung", true)) {
            List<String> validCops = new ArrayList<>();

            validCops.addAll(DataStorage.cops.getCops().keySet());

            server.loadMembers(serverMember -> {

                if (!validCops.contains(serverMember.getId())) {
                    for (Role role : serverMember.getRoles()) {
                        if(!role.getName().equalsIgnoreCase("Bürger") && !role.getName().equalsIgnoreCase("cloudliferp.de Teammitglied"))
                        server.removeRoleFromMember(serverMember.getId(),role).queue();
                    }
                } else {
                    serverMember.modifyNickname("[" + DataStorage.cops.getCops().get(serverMember.getId()) + "]" + serverMember.getNickname().split("]")[1]).queue();
                }
            });

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
