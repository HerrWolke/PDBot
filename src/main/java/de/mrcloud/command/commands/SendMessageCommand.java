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
import java.time.chrono.IsoEra;

import static de.mrcloud.main.Main.server;

public class SendMessageCommand extends Command {

    public SendMessageCommand() {
        super("send", "", "", Category.STAFF);
    }

    @Override
    public boolean execute(GuildMessageReceivedEvent e, String[] args) {
        Member member = e.getMember();
        TextChannel txtChannel = e.getChannel();
        Member pdBot = e.getGuild().getMemberById(812773728660357151L);

        if (JDAUtils.hasRole(member, "Personalabteilung", true)) {

            EmbedBuilder embBuilder = new EmbedBuilder();
            embBuilder.setTitle("Dienstliste");
            embBuilder.setAuthor(pdBot.getUser().getName(), pdBot.getUser().getAvatarUrl(), pdBot.getUser().getAvatarUrl());
            embBuilder.setColor(Color.decode("#d63031"));
            embBuilder.setDescription("Niemand ist im Dienst");
            embBuilder.setThumbnail(pdBot.getUser().getAvatarUrl());
            embBuilder.setTitle("Dienst Liste");
            txtChannel.sendMessage("Reagiere auf den :white_check_mark: um In den Dienst versetzt zu werden! Denke daran das du wenn du außer Dienst gehst wieder reagierst um nicht mehr im Dienst zu sein! Wer dies nicht befolgt/beachtet muss mit Harten Konsequenzen rechnen. \n \n Solltest du aktiviert haben, dass auf Discord angezeigt wird, welches Spiel du spielst, musst/sollst du dies nicht manuell machen. \n \n :x: Solltest du nur benutzen, wenn du die Spieleaktivität auf Discord anhast und auf Cloudlife bist, aber nicht im Dienst bist!").queue(message -> {
                message.editMessage(embBuilder.build()).queue();
                message.addReaction("✅").queue();
                message.addReaction("❌").queue();
                DataStorage.settings.updateSetting("dl_id", e.getChannel().getId());
                DataStorage.settings.updateSetting("dm_id", message.getId());
            });


        }  else {
            JDAUtils.redBuilder("Error","Du hast keine Berechtigung diesen Command zu benutzen. Du benötigst die Rolle " + server.getRolesByName("Personalabteilung",true).get(0).getAsMention() + " !",member,e.getChannel(),20);
        }

        return false;
    }

    @Override
    public boolean execute(MessageReceivedEvent e, String[] args) {
        return false;
    }
}
