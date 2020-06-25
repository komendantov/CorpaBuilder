package ru.komendantov.corpabuilder.services;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

@Service
public class MystemService {

    private Process getMystemProcess(File targetFile) throws IOException {
        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");
        ProcessBuilder builder = new ProcessBuilder();
        String mystemPath = System.getProperty("mystemPath");
        String command;
      //  if (isWindows) {
            command = mystemPath + " -cisd --eng-gr --generate-all --format json " + targetFile.getCanonicalPath();
      //  } else {
      //      command = mystemPath + " -cisd --eng-gr --generate-all --format json " + targetFile.getCanonicalPath();
      //  }
        Process process = Runtime.getRuntime().exec(command);
        builder.directory(new File(System.getProperty("user.dir")));
        return process;
    }

    public String analyseText(File targetFile) throws IOException {
        Process mystemProcess = getMystemProcess(targetFile);
        BufferedReader input = new BufferedReader(new InputStreamReader(mystemProcess.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = input.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    public String analyseText(String text) throws IOException {
        File f = File.createTempFile("mystemRes", "temp");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write(text);
        bw.close();
        String result = analyseText(f);
        f.delete();
        return result;
    }
}
