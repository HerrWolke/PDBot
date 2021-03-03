package de.mrcloud.listeners;

import de.mrcloud.logging.Logger;
import de.mrcloud.logging.Logging;
import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@Logging(displayName = "ReactionListener")
public class ReactionListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent e) {
        super.onGuildMessageReactionAdd(e);

        if (e.getUser().isBot()) return;

        MessageReaction.ReactionEmote reactionEmote = e.getReactionEmote();
        String emoteName = reactionEmote.getName();
        TextChannel txtChannel = e.getChannel();
        Member member = e.getMember();
        String messageID = e.getMessageId();
        Logger logger = new Logger(this, Logger.Lvl.DEFAULT);

        if (txtChannel.getId().equals(DataStorage.settings.getSettings().get("dl_id"))) {
            if (JDAUtils.hasRole(member, "Los Santos Police Department", true)) {
                if (emoteName.equalsIgnoreCase("✅")) {
                    if (!DataStorage.onDuty.getListOfOnDuty().contains(e.getMember().getIdLong())) {
                        DataStorage.onDuty.addOnDuty(member);
                        logger.log(Logger.LoggerLevel.INFO, member, " ist dem Dienst manuell beigetreten!");
                        JDAUtils.getChannelMessage(txtChannel, messageID).removeReaction("❌", e.getUser()).queue();
                        JDAUtils.addRoleToMemberByName(e.getMember(), "Im Dienst | Liste");
                        if (DataStorage.onDuty.getHasLeftDuty().contains(member.getIdLong()))
                            DataStorage.onDuty.removeHasLeftDuty(member);
                    } else {
                        JDAUtils.privateRedBuilder("Ooops", "Du bist bereits im Dienst!", e.getUser());
                    }


                } else if (emoteName.equalsIgnoreCase("❌")) {
                    if (DataStorage.onDuty.getListOfOnDuty().contains(e.getMember().getIdLong())) {
                        DataStorage.onDuty.removeOnDuty(member);
                        logger.log(Logger.LoggerLevel.INFO, member, " hat den Dienst manuell verlassen!");
                        DataStorage.onDuty.addHasLeftDuty(member);
                        JDAUtils.removeRoleFromMember(e.getMember(), "Im Dienst | Liste");
                    } else {
                        JDAUtils.privateRedBuilder("Ooops", "Du bist nicht im Dienst!", e.getUser());
                        JDAUtils.getChannelMessage(txtChannel, messageID).removeReaction("❌", e.getUser()).queue();
                    }
                }


            } else {
                if (e.getUser().hasPrivateChannel())
                    JDAUtils.privateRedBuilder("Ooops", "Du bist kein Cop. Du kannst den Dienst nicht antreten oder verlassen!", e.getUser());
                JDAUtils.getChannelMessage(txtChannel, messageID).removeReaction("✅", e.getUser()).queue();
                JDAUtils.getChannelMessage(txtChannel, messageID).removeReaction("❌", e.getUser()).queue();
            }
        }
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent e) {
        super.onMessageReactionRemove(e);

        MessageReaction.ReactionEmote reactionEmote = e.getReactionEmote();
        String emoteName = reactionEmote.getName();
        MessageChannel txtChannel = e.getChannel();
        Member member = e.getMember();
        String messageID = e.getMessageId();
        Logger logger = new Logger(this, Logger.Lvl.DEFAULT);

        if (txtChannel.getId().equals(DataStorage.settings.getSettings().get("dl_id"))) {
            if (JDAUtils.hasRole(member, "Los Santos Police Department", true)) {
                if (emoteName.equalsIgnoreCase("❌")) {
                    DataStorage.onDuty.removeHasLeftDuty(member);
                } else if (emoteName.equalsIgnoreCase("✅")) {
                    DataStorage.onDuty.removeOnDuty(member);
                    logger.log(Logger.LoggerLevel.INFO, member, " hat den Dienst manuell verlassen!");
                    JDAUtils.removeRoleFromMember(e.getMember(), "Im Dienst | Liste");
                }
            }
        }
    }
}
