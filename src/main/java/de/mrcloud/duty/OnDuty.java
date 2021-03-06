package de.mrcloud.duty;

import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.JDAUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static de.mrcloud.main.Main.server;

public class OnDuty {
    private List<Long> listOfOnDuty;
    private List<Long> hasLeftDuty;

    public OnDuty() {
        listOfOnDuty = new ArrayList<>();
        hasLeftDuty = new ArrayList<>();
    }

    public List<Long> getListOfOnDuty() {
        return listOfOnDuty;
    }

    public void setListOfOnDuty(List<Long> listOfOnDuty) {
        this.listOfOnDuty = listOfOnDuty;
    }

    public void addOnDuty(Member member) {
        listOfOnDuty.add(member.getIdLong());
        Message onDutyMessage = JDAUtils.getChannelMessage(server.getTextChannelById(DataStorage.settings.getSettings().get("dl_id")), DataStorage.settings.getSettings().get("dm_id"));
        StringBuilder newOnDutyList = new StringBuilder();
        LinkedHashMap<Integer, Long> dutyNumbersWithIds = new LinkedHashMap<>();

        if (DataStorage.cops.getCops().get(Long.toString(member.getIdLong())) == null) {
            String highestDutyNumber = (String) DataStorage.cops.getCops().values().toArray()[DataStorage.cops.getCops().size() - 1];
            int dutyNumberInt = Integer.parseInt(highestDutyNumber.split(" ")[1]) + 1;

            DataStorage.cops.addMedic(member.getId(), "OL " + dutyNumberInt);
        }

        for (long id : listOfOnDuty) {
            dutyNumbersWithIds.put(Integer.valueOf(DataStorage.cops.getCops().get(Long.toString(id)).split(" ")[1]), id);
        }


        Integer[] dutyNumbers = dutyNumbersWithIds.keySet().toArray(Integer[]::new);
        Arrays.sort(dutyNumbers);

        List<Long> dutyNumbersFinal = new ArrayList<>();


        for (int dutyNumber : dutyNumbers) {
            dutyNumbersFinal.add(dutyNumbersWithIds.get(dutyNumber));
        }

        for (long id : dutyNumbersFinal)
            newOnDutyList.append(server.getMemberById(id).getAsMention()).append(" | ").append(DataStorage.cops.getCops().get(Long.toString(id))).append("\n");


        if (listOfOnDuty.isEmpty()) newOnDutyList.append("Es ist niemand im Dienst");

        onDutyMessage.editMessage(new EmbedBuilder(onDutyMessage.getEmbeds().get(0)).setDescription(newOnDutyList.toString()).build()).queue();
    }

    public void removeOnDuty(Member member) {




        listOfOnDuty.remove(member.getIdLong());



        Message onDutyMessage = JDAUtils.getChannelMessage(server.getTextChannelById(DataStorage.settings.getSettings().get("dl_id")), DataStorage.settings.getSettings().get("dm_id"));
        StringBuilder newOnDutyList = new StringBuilder();

        LinkedHashMap<Integer, Long> dutyNumbersWithIds = new LinkedHashMap<>();

        for (long id : listOfOnDuty) {
            dutyNumbersWithIds.put(Integer.valueOf(DataStorage.cops.getCops().get(Long.toString(id)).split(" ")[1]), id);
        }




        Integer[] dutyNumbers = dutyNumbersWithIds.keySet().toArray(Integer[]::new);
        Arrays.sort(dutyNumbers);

        List<Long> dutyNumbersFinal = new ArrayList<>();


        for (int dutyNumber : dutyNumbers) {
            dutyNumbersFinal.add(dutyNumbersWithIds.get(dutyNumber));
        }

        for (long id : dutyNumbersFinal) {
            newOnDutyList.append(server.getMemberById(id).getAsMention()).append(" | ").append(DataStorage.cops.getCops().get(Long.toString(id))).append("\n");
        }


        if (listOfOnDuty.isEmpty()) newOnDutyList.append("Es ist niemand im Dienst");

        onDutyMessage.editMessage(new EmbedBuilder(onDutyMessage.getEmbeds().get(0)).setDescription(newOnDutyList.toString()).build()).queue();
    }

    public void clearOnDuty() {
        Message onDutyMessage = JDAUtils.getChannelMessage(server.getTextChannelById(DataStorage.settings.getSettings().get("dl_id")), DataStorage.settings.getSettings().get("dm_id"));
        StringBuilder newOnDutyList = new StringBuilder();
        listOfOnDuty.clear();

        newOnDutyList.append("Es ist niemand im Dienst");


        onDutyMessage.editMessage(new EmbedBuilder(onDutyMessage.getEmbeds().get(0)).setDescription(newOnDutyList.toString()).build()).queue();
    }

    public List<Long> getHasLeftDuty() {
        return hasLeftDuty;
    }

    public void setHasLeftDuty(List<Long> hasLeftDuty) {
        this.hasLeftDuty = hasLeftDuty;
    }

    public void addHasLeftDuty(Member member) {
        hasLeftDuty.add(member.getIdLong());
    }

    public void removeHasLeftDuty(Member member) {
        hasLeftDuty.remove(member.getIdLong());
    }

}
