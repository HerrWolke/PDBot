package de.mrcloud.main;


import de.mrcloud.command.Registerer;
import de.mrcloud.duty.OnDuty;
import de.mrcloud.listeners.CommandExecutor;
import de.mrcloud.listeners.OnDutyListener;
import de.mrcloud.listeners.ReactionListener;
import de.mrcloud.listeners.RoleUpdateListener;
import de.mrcloud.logging.LoggingSystem;
import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import de.mrcloud.utils.Static;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {
    public static ShardManager shardMan;
    public static Guild server;
    DefaultShardManagerBuilder builder;


    public Main() throws LoginException {
        builder = DefaultShardManagerBuilder.createDefault(Static.TOKEN, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_INVITES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.DIRECT_MESSAGES).setMemberCachePolicy(MemberCachePolicy.ALL).enableCache(CacheFlag.ACTIVITY);

        builder.setAutoReconnect(true);
        builder.setRequestTimeoutRetry(true);
        builder.addEventListeners(new ReactionListener(), new OnDutyListener(), new CommandExecutor(), new RoleUpdateListener());


        shardMan = builder.build();

        new Registerer();

        LoggingSystem loggingSystem = new LoggingSystem();
        System.out.println(loggingSystem.initialize());
        DataStorage.loadCops();
        DataStorage.loadSettings();

        DataStorage.onDuty = new OnDuty();


        while (shardMan.getGuilds().isEmpty()) {
            System.out.print("");
        }

        server = shardMan.getGuilds().get(0);

        turnOffListener();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    Random random = new Random();
                    int randomInt = random.nextInt(30);

                    if (isBetween(randomInt, -1, 5)) {
                        shardMan.setActivity(Activity.playing("kommt zum Police Department!"));
                        Thread.sleep(120000);
                    } else if (isBetween(randomInt, 5, 10)) {
                        shardMan.setActivity(Activity.playing("CloudLifeRP.de for life!"));
                        Thread.sleep(30000);
                    } else if (isBetween(randomInt, 11, 13)) {
                        List<Member> couldBeChosen = new ArrayList<>();
                        server.loadMembers().onSuccess(members -> {
                            for (Member member : members) {
                                if (JDAUtils.hasRole(member, "Los Santos Police Department", true)) {
                                    couldBeChosen.add(member);
                                }
                            }
                        });
                        Member member = couldBeChosen.get(random.nextInt(couldBeChosen.size()));
                        if (member.getNickname() == null)
                            shardMan.setActivity(Activity.playing(member.getEffectiveName() + " ist lost!"));
                        else
                            shardMan.setActivity(Activity.playing(member.getNickname() + " ist lost!"));
                        Thread.sleep(45000);
                    } else if (isBetween(randomInt, 13, 15)) {
                        shardMan.setActivity(Activity.playing("Funkdisziplin!!"));
                        Thread.sleep(34000);
                    } else if (isBetween(randomInt, 15, 18)) {
                        shardMan.setActivity(Activity.playing("kein Copbaiting :D"));
                        Thread.sleep(45000);
                    } else if (isBetween(randomInt, 19, 21)) {
                        shardMan.setActivity(Activity.playing("die Sniper ist nicht op"));
                        Thread.sleep(20000);
                    } else if (isBetween(randomInt, 20, 25)) {
                        shardMan.setActivity(Activity.playing("Heli fliegen muss gelernt sein!"));
                        Thread.sleep(30000);
                    } else if (isBetween(randomInt, 25, 28)) {
                        shardMan.setActivity(Activity.playing("haltet euch an die STVO"));
                        Thread.sleep(120000);
                    } else if (isBetween(randomInt, 28, 31)) {
                        shardMan.setActivity(Activity.playing("wo sind die Ladenräube?"));
                        Thread.sleep(40000);
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public static void main(String[] args) throws LoginException {
        new Main();


    }

    public void turnOffListener() {
        new Thread(() -> {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            try {
                while ((line = reader.readLine()) != null) {
                    if (line.equalsIgnoreCase("Stop")) {
                        if (shardMan != null) {
                            shardMan.setStatus(OnlineStatus.OFFLINE);
                            shardMan.setActivity(Activity.listening("offline"));
                            shardMan.shutdown();
                            System.out.println("Bot wird heruntergefahren");
                            System.exit(1);

                        }
                    } else {
                        System.out.println("Bitte benutz stop!");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public boolean isBetween(int number, int between, int between2) {
        return number > between && number < between2;
    }
}
