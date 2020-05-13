package ru.komendantov.corpabuilder.services;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class MystemService {

    private Process getMystemProcess(File targetFile) throws IOException {
        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");
        ProcessBuilder builder = new ProcessBuilder();
        String command;
        if (isWindows) {
            command = "";
        } else {
            command = "./mystem -cisd --eng-gr --generate-all --format json " + targetFile.getCanonicalPath();
        }
        Process process = Runtime.getRuntime().exec(command);
        builder.directory(new File(System.getProperty("user.dir")));
        return process;
    }

    public String analyseText(File targetFile) throws IOException, InterruptedException {
        Process mystemProcess = getMystemProcess(targetFile);
        BufferedReader input = new BufferedReader(new InputStreamReader(mystemProcess.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = input.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    public String analyseText(String text) throws IOException, InterruptedException {
        File f = File.createTempFile("mystemRes", "temp");
        //   f.createNewFile();
        //   FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write(text);
        bw.close();
        return analyseText(f);
    }
}
