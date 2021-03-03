package de.mrcloud.logging;

import de.mrcloud.utils.DataStorage;
import de.mrcloud.utils.Static;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@Logging(displayName = "LoggingSystem")
public class LoggingSystem {
    Logger logger = new Logger(this, Logger.Lvl.DEFAULT);

    public boolean initialize() {
        DataStorage.dateLogName = new SimpleDateFormat("dd.MM.yy").format(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime());

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                logger.log(Logger.LoggerLevel.INFO, "Getting the new date for the Logging System");
                DataStorage.dateLogName = new SimpleDateFormat("dd.MM.yy").format(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime());
            }
        };
        timer.scheduleAtFixedRate(task, 0, TimeUnit.DAYS.toMillis(1));
        new File("Logs").mkdirs();
        new File("BoostLogs").mkdirs();
        File todaysLogFile = new File(Static.LOG_FOLDER + DataStorage.dateLogName + ".log");

        if (!todaysLogFile.exists()) {
            try {
                return todaysLogFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
