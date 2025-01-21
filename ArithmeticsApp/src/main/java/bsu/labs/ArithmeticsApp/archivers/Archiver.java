package bsu.labs.ArithmeticsApp.archivers;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class Archiver {
    private static Archiver instance;
    private List<String> filesNames;

    private Archiver() {
        filesNames = new ArrayList<>();
    }

    public static Archiver getInstance() {
        if (instance == null) {
            instance = new Archiver();
        }
        return instance;
    }

    public void addFile(String filename) {
        filesNames.add("src/main/resources/" + filename);
    }

    public void archiveFiles(String zipFilename) {

        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("src/main/resources/" + zipFilename));
        ) {

            for (String fileName : filesNames) {
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOut.putNextEntry(zipEntry);
                FileInputStream fis = new FileInputStream(fileName);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zipOut.write(buffer);
                zipOut.closeEntry();
            }
            zipOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getFiles() {
        return filesNames;
    }

    public void clearArchive() {
        filesNames.clear();
    }
}