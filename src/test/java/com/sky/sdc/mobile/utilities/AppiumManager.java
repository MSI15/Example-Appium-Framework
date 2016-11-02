package com.sky.sdc.mobile.utilities;

/**
 * Created by msi15 on 01/11/2016.
 */
import org.apache.log4j.Logger;

public class AppiumManager {

    private final static Logger log = Logger.getLogger(AppiumManager.class);

    CommandPrompt cp = new CommandPrompt();
    Ports ap = new Ports();
    String output;

    /**
     * start appium with default arguments
     */
    public void startDefaultAppium() throws Exception {
        output = cp.runCommand("appium --session-override");

        String[] lines = output.split("\n");

        for (int i = 1; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\s+", "");
            if (lines[i].contains("Could not start")) {
                log.info("\n Could not start Appium");
                System.exit(0);
            }
        }
    }

    /**
     * start appium with auto generated ports : appium port, chrome port, and
     * bootstap port
     */
    public String startAppium() throws Exception {
        String port = ap.getPort();
        String chromePort = ap.getPort();
        String bootstrapPort = ap.getPort();

        String command = "appium --session-override -p " + port + " --chromedriver-port " + chromePort + " -bp "
                + bootstrapPort;
        log.info(command);
        String output = cp.runCommand(command);

        if (output.contains("not")) {
            log.info("\nAppium is not started");
            System.exit(0);
        }

        return port;
    }

    /**
     * start appium with modified arguments : appium port, chrome port, and
     * bootstap port as user pass port number
     *
     * @param port
     * @param chromePort
     * @param bootstrapPort
     */
    public void startAppium(String port, String chromePort, String bootstrapPort) throws Exception {
        String command = "appium --session-override -p " + port + " --chromedriver-port " + chromePort + " -bp "
                + bootstrapPort;
        log.info(command);
        String output = cp.runCommand(command);
        log.info(output);
    }

}


