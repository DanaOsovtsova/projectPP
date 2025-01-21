package bsu.labs.ArithmeticsApp.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
public class InputFolderCleaner {

    private static final String INPUT_FOLDER_PATH = "src/main/resources/";

    public void cleanInputFolder() {
        List<String> folders = List.of("inputs", "outputs", "archives", "decrypts", "outputs", "encrypts", "unarchived");
        for (String fl : folders) {
            File folder = new File(INPUT_FOLDER_PATH + fl);

            if (!folder.exists()) {
                return;
            }

            if (!folder.isDirectory()) {
                return;
            }

            File[] files = folder.listFiles();
            if (files == null) {
                return;
            }

            for (File file : files) {
                try {
                    Files.deleteIfExists(file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
