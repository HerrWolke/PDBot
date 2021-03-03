package de.mrcloud.command.commands;

import de.mrcloud.command.Command;
import de.mrcloud.utils.DataStorage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "This list ^_^", "!help", Category.STAFF);
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent e, String[] args) {
        Member member = e.getMember();
        TextChannel channel = e.getChannel();
        User author = e.getAuthor();


        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(author.getName(), author.getAvatarUrl(), author.getAvatarUrl());
        builder.setColor(Color.CYAN);
        for (Command cmd : DataStorage.commands) {
            builder.addField(cmd.name, cmd.description + "\n \n Benutze: " + cmd.usage, true);
        }
        channel.sendMessage(builder.build()).queue();
        return false;
    }

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
        return false;
    }
}
