package com.sky.sdc.mobile.utilities;

/**
 * Created by msi15 on 01/11/2016.
 */
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandPrompt {

    private final static Logger log = Logger.getLogger(CommandPrompt.class);

    Process p;
    ProcessBuilder builder;

    public String runCommand(String command) throws InterruptedException, IOException
    {
        p = Runtime.getRuntime().exec(command);

        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line="";
        String allLine="";
        while((line=r.readLine()) != null){
            allLine=allLine+""+line+"\n";
            if(line.contains("started on") || line.contains("emulator: "))
                break;
        }
        return allLine;
    }


    public void stopServer() throws IOException {
        if (p != null) {
            p.destroy();
        }
        log.info("Stopped!");
    }
}
