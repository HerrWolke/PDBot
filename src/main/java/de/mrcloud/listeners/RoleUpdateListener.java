package de.mrcloud.listeners;

import de.mrcloud.utils.DataStorage;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class RoleUpdateListener extends ListenerAdapter {
    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent e) {
        super.onGuildMemberRoleRemove(e);

        Member member = e.getMember();
        List<Role> roles = e.getRoles();

        if (roles.get(0).getName().equalsIgnoreCase("Los Santos Police Department") && !DataStorage.cops.getCops().containsKey(member.getId())) {
            DataStorage.cops.removeMedic(member.getId(), false);
        }
    }
}
