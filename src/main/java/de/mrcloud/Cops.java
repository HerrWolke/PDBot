package de.mrcloud;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.mrcloud.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class Cops {
    private LinkedHashMap<String, String> cops;

    public LinkedHashMap<String, String> getCops() {
        return cops;
    }

    public void setCops(LinkedHashMap<String, String> cops) {
        this.cops = cops;
    }


    public void addMedic(String id, String dutyNumber) {
        System.out.println(dutyNumber);
        cops.put(id, dutyNumber);

        List<Map.Entry<String, String>> list = new ArrayList<>(cops.entrySet());

        list.sort(new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return Integer.compare(Integer.parseInt(o1.getValue().split(" ")[1]),Integer.parseInt(o2.getValue().split(" ")[1]));
            }
        });


        cops.clear();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        list.forEach(entry2 -> cops.put(entry2.getKey(), entry2.getValue()));


        File file = new File("cops.json");

        try {
            objectMapper.writeValue(file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean setCop(String id, String dutyNumber) {
        boolean success = false;

        for (Map.Entry<String, String> entry : cops.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(dutyNumber)) {
                cops.remove(entry.getKey());
                cops.put(id, entry.getValue());

                List<Map.Entry<String, String>> list = new ArrayList<>(cops.entrySet());

                Collections.sort(list, new Comparator<Map.Entry<String, String>>() {

                    @Override
                    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                        return o1.getValue().compareTo(o2.getValue());
                    }
                });


                cops.clear();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                list.forEach(entry2 -> cops.put(entry2.getKey(), entry2.getValue()));

                success = true;
                break;
            }

        }


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        File file = new File("cops.json");

        try {
            objectMapper.writeValue(file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    public void editMedic(String id, String newDutyNumber) {
        cops.replace(id, newDutyNumber);

        List<Map.Entry<String, String>> list = new ArrayList<>(cops.entrySet());

        list.sort(new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return Integer.compare(Integer.parseInt(o1.getValue().split(" ")[1]),Integer.parseInt(o2.getValue().split(" ")[1]));
            }
        });


        cops.clear();
        list.forEach(entry2 -> cops.put(entry2.getKey(), entry2.getValue()));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        File file = new File("cops.json");

        try {
            objectMapper.writeValue(file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map.Entry<String, String> removeMedic(String id, boolean lowerDutyNumbers) {
        Map.Entry<String, String> entryFound = null;
        for (Map.Entry<String, String> entry : cops.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(id) || entry.getValue().equalsIgnoreCase(id)) {
                entryFound = entry;
                break;
            }
        }

        if (entryFound == null) return null;

        id = entryFound.getKey();
        String dutyNumber = cops.get(id);

        int dutyNumberInt = Integer.parseInt(cops.get(id).split(" ")[1]);
        cops.remove(id);

        for (String ids : cops.keySet()) {
            int personNumber = Integer.parseInt(cops.get(ids).split(" ")[1]);
            if (dutyNumberInt < personNumber) {
                personNumber--;
                editMedic(ids, "ol " + personNumber);
            }
        }


        if (Utils.hasKey(cops, id))
            cops.put("812773728660357151" + 1, dutyNumber);

        List<Map.Entry<String, String>> list = new ArrayList<>(cops.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {

            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });


        cops.clear();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        list.forEach(entry -> cops.put(entry.getKey(), entry.getValue()));
        System.out.println(Arrays.toString(cops.entrySet().toArray()));


        File file = new File("cops.json");

        try {
            objectMapper.writeValue(file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entryFound;
    }


}
