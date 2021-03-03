package de.mrcloud.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    public static void ausgeben(String Aussage) {
        System.out.println("                                            ");
        System.out.println("                                            ");
        System.out.println("                                            ");
        System.out.println("--------------------------------------------");
        System.out.println(Aussage);
        System.out.println("--------------------------------------------");
        System.out.println("                                            ");
        System.out.println("                                            ");
        System.out.println("                                            ");
    }

    public static void Err(String ErrorNachricht) {
        System.err.println(ErrorNachricht);
    }

    public static void writeString(File NameOfFile, String content, boolean append) {
        try (FileWriter FileWriterName = new FileWriter(NameOfFile, append);
             BufferedWriter writer = new BufferedWriter(FileWriterName)) {

            writer.write(content);
            writer.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void writeStringOften(File NameOfFile, String content, int TimeToWrite) {
        try (FileWriter FileWriterName = new FileWriter(NameOfFile, true);
             BufferedWriter Writer = new BufferedWriter(FileWriterName)) {

            int i = 0;
            while (i < TimeToWrite) {
                Writer.write(content);
                Writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeInt(File FilePath, int content) {
        try (FileWriter FileWriterName = new FileWriter(FilePath, true);
             BufferedWriter Writer = new BufferedWriter(FileWriterName)) {

            Writer.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDouble(File FilePath, double content1) {
        try (FileWriter FileWriterName = new FileWriter(FilePath, true);
             BufferedWriter Writer = new BufferedWriter(FileWriterName)) {

            String content = Double.toString(content1);
            Writer.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readString(File file) {
        try {
            Scanner scanner = new Scanner(file);
            String toReturn = scanner.next();
            scanner.close();
            return toReturn;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean listContainsIgnoreCase(List<String> list, String toCheck) {
        for (String item : list) {
            if (item.equalsIgnoreCase(toCheck)) {
                return true;
            }
        }
        return false;
    }

    public static String getDate() {
        return new SimpleDateFormat("d:MM:yyyy HH:mm:ss").format(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime());
    }

    public static boolean hasKey(LinkedHashMap<String, String> map, Object key) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }
}

