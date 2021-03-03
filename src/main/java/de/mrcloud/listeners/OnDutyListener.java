package de.mrcloud.listeners;

import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.user.UserActivityEndEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.temporal.ChronoUnit;


public class OnDutyListener extends ListenerAdapter {
    @Override
    public void onUserActivityStart(@NotNull UserActivityStartEvent e) {
        super.onUserActivityStart(e);

        Activity activity = e.getNewActivity();


        long timeElapsed = 0;
        if (activity.getTimestamps() != null)
            timeElapsed = activity.getTimestamps().getElapsedTime(ChronoUnit.SECONDS);


        if ((activity.getName().equalsIgnoreCase("cloudliferp.de") || (activity.asRichPresence() != null && activity.asRichPresence().getDetails() != null && activity.asRichPresence().getDetails().contains("cloudliferp.de"))) && JDAUtils.hasRole(e.getMember(), "Los Santos Police Department", true) && !DataStorage.onDuty.getListOfOnDuty().contains(e.getMember().getIdLong()) && (!DataStorage.onDuty.getHasLeftDuty().contains(e.getMember().getIdLong()) || timeElapsed < 10)) {
            DataStorage.onDuty.addOnDuty(e.getMember());
            JDAUtils.addRoleToMemberByName(e.getMember(), "Im Dienst | Liste");
        }
    }


    @Override
    public void onUserActivityEnd(@NotNull UserActivityEndEvent e) {
        super.onUserActivityEnd(e);

        Activity activity = e.getOldActivity();

        if ((activity.getName().equalsIgnoreCase("cloudliferp.de") || (activity.asRichPresence() != null && activity.asRichPresence().getDetails() != null && activity.asRichPresence().getDetails().contains("cloudliferp.de"))) && JDAUtils.hasRole(e.getMember(), "Los Santos Police Department", true)) {
            boolean isCloudLife = false;

            for (Activity activity1 : e.getMember().getActivities()) {
                if (activity1.getName().equalsIgnoreCase("cloudliferp.de"))
                    isCloudLife = true;
            }

            if (!isCloudLife) {
                JDAUtils.removeRoleFromMember(e.getMember(), "Im Dienst | Liste");
                DataStorage.onDuty.removeOnDuty(e.getMember());
            }
        }
    }


}
