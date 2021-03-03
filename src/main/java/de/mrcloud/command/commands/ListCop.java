package de.mrcloud.command.commands;

import de.mrcloud.command.Command;
import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static de.mrcloud.main.Main.server;

public class ListCop extends Command {
    public ListCop() {
        super("listcops", "", "", Category.STAFF);
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent e, String[] args) {
        Member member = e.getMember();
        TextChannel txtChannel = e.getChannel();
        if (JDAUtils.hasRole(member, "Personalabteilung", true)) {
            StringBuilder builder = new StringBuilder();
            List<String> toRemove = new ArrayList<>();

            for (String key : DataStorage.cops.getCops().keySet()) {
                if (!key.matches("\\d*"))
                    continue;
                if (server.getMemberById(key) == null) {
                    toRemove.add(key);
                    continue;
                }
                if (key.equals("812773728660357151")) {
                    builder.append("Empty").append(" | ").append(DataStorage.cops.getCops().get(key)).append("\n");
                    continue;
                }

                if (server.getMemberById(key).getNickname() == null)
                    builder.append(server.getMemberById(key).getEffectiveName()).append(" | ").append(DataStorage.cops.getCops().get(key)).append("\n");
                else
                    builder.append(server.getMemberById(key).getNickname()).append(" | ").append(DataStorage.cops.getCops().get(key)).append("\n");
            }

            for (String rem : toRemove) {
                DataStorage.cops.removeMedic(rem);
            }


            EmbedBuilder embBuilder = new EmbedBuilder();
            embBuilder.setTitle("Cop Liste");
            embBuilder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
            embBuilder.setColor(Color.decode("#2ecc71"));
            embBuilder.setDescription(builder);
            embBuilder.setFooter("Momentane Cops Anzahl: " + DataStorage.cops.getCops().size());
            txtChannel.sendMessage(embBuilder.build()).queue();
        }

        return false;
    }

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
        return false;
    }
}
