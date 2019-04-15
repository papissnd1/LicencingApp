package utils;

import java.io.IOException;

/**
 * Created by Papis Ndiaye on 12/03/2016.
 */
public class InternetAccess {
    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try{
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
