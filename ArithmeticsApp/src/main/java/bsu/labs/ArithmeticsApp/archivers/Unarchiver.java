package bsu.labs.ArithmeticsApp.archivers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
@Slf4j
public class Unarchiver {
    private ClassLoader classLoader = getClass().getClassLoader();
    public void extractArchive(String archiveFile, String destinationFolder) throws IOException {
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream("src/main/resources/" + archiveFile));
        byte[] buffer = new byte[1024];
        ZipEntry entry = zipIn.getNextEntry();
        String[] name = entry.getName().split("/");
        while (entry != null) {
            String filePath = "src/main/resources/" + destinationFolder + File.separator + name[name.length - 1];
            if (!entry.isDirectory()) {
                new File(filePath).getParentFile().mkdirs();
                FileOutputStream fos = new FileOutputStream(filePath);
                int length;
                while ((length = zipIn.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                fos.close();
            } else {
                new File(filePath).mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
    }
}
