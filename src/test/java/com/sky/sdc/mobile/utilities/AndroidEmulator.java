package com.sky.sdc.mobile.utilities;

/**
 * Created by msi15 on 01/11/2016.
 */

import org.apache.log4j.Logger;

import java.io.IOException;

public class AndroidEmulator {

    private final static Logger log = Logger.getLogger(AndroidEmulator.class);

    CommandPrompt cp = new CommandPrompt();
    private String launchEmulator, defaultEmulator;
    private String[] lines;
    private String availableEmulators;
    private String[] emulatorList;

    public String getDefaultEmulator() {
        return defaultEmulator;
    }

    public void startDefaultEmulator() throws Exception {
        String availableEmulators = cp.runCommand("emulator -list-avds");
        // check its not null so that we don't end up in NPE
        if (null != availableEmulators) {
            lines = availableEmulators.split("\n");
        }
        if (null == lines || lines.length == 0) {
            log.info("No Emulators available. Creating new emulator");
            createEmulator();
            defaultEmulator = "testEmulator";
            launchEmulator = "emulator -avd testEmulator";
        } else {
            defaultEmulator = lines[0].toString();
            launchEmulator = "emulator -avd " + defaultEmulator;
            log.info("\n launching emulator using : " + launchEmulator);
        }
        cp.runCommand(launchEmulator);
    }

    public void createEmulator() throws InterruptedException, IOException {
        String output = cp.runCommand("android create avd -n testEmulator -t 8 --abi default/x86_64 --skin WVGA800 -f");
        String[] lines = output.split("\n");
        for (int i = 1; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\s+", "");
            if (lines[i].contains("Do you wish to create a custom hardware profile [no]")) {
                cp.runCommand("no");
            }
        }
        availableEmulators = cp.runCommand("emulator -list-avds");
        emulatorList = availableEmulators.split("\n");
        for (int i = 1; i < emulatorList.length; i++) {
            lines[i] = lines[i].replaceAll("\\s+", "");
            if (lines[i].contains("testEmulator")) {
                log.info("\n test emulator created");
            }
        }
    }

    public void startEmulator(String emulatorName) throws InterruptedException, IOException {
        String availableEmulators = cp.runCommand("emulator -list-avds");
        String[] emulatorList = availableEmulators.split("\n");

        for (int i = 0; i <= emulatorList.length; i++) {
            emulatorList[i] = emulatorList[i].replaceAll("\\s+", "");
            if (emulatorList[i].contains(emulatorName)) {
                log.info("\n Emulator avilable");
                launchEmulator = "emulator -avd " + emulatorName;
                log.info("\n launching emulator using : " + launchEmulator);
                cp.runCommand(launchEmulator);
                break;
            }
            else {
                if(i == emulatorList.length) {
                    log.info("\n Emulator not available. Create new emulator : " + emulatorName);
                    createEmulator(emulatorName);
                }
            }
        }
    }

    public void createEmulator(String emulatorName) throws InterruptedException, IOException {
        String emulatorLaunchCmd = "android create avd -n " + emulatorName + " -t 8 --abi default/x86_64 --skin WVGA800 -f";
        log.info("\n Create Emulator with : " + emulatorLaunchCmd);
        String output = cp.runCommand(emulatorLaunchCmd);
        lines = output.split("\n");
        for (int i = 1; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\s+", "");
			/*if(lines[i].contains("Android 5.0.1 is a basic Android platform.")){
				break;
			}
			*/
            if (lines[i].contains("Do you wish to create a custom hardware profile [no]")) {
                cp.runCommand("no");
            }
        }
        availableEmulators = cp.runCommand("emulator -list-avds");
        emulatorList = availableEmulators.split("\n");
        for (int i = 1; i < emulatorList.length; i++) {
            lines[i] = lines[i].replaceAll("\\s+", "");
            if (lines[i].contains(emulatorName)) {
                log.info("\n Emulator created");
            }
        }
    }
}

