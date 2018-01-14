package ru.deysa.investment.assistant.server.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Deys on 15.06.2015.
 */
@Service
public class RuntimeManager {

    private final Logger log = LoggerFactory.getLogger(RuntimeManager.class);

    private String slimerPath;

    private String slimerScriptFolder;

    public void run(String command) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(command);
        log.info("Command:" + command);

        BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        log.info("Output:  " + outputReader.readLine());

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        log.info("Error:  " + errorReader.readLine());

        process.waitFor();
    }

}
