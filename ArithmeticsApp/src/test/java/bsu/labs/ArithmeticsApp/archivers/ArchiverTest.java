package bsu.labs.ArithmeticsApp.archivers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ArchiverTest {
    private static final String TEST_RESOURCE_DIRECTORY = "src/main/resources/";
    private static final String TEST_ARCHIVE_NAME = "tests/test.zip";

    private Archiver archiver;

    @BeforeEach
    public void setup() {
        archiver = Archiver.getInstance();
    }

    @AfterEach
    public void cleanup() {
        archiver.clearArchive();
        deleteTestArchive();
    }

    private void deleteTestArchive() {
        File archiveFile = new File(TEST_RESOURCE_DIRECTORY + TEST_ARCHIVE_NAME);
        if (archiveFile.exists()) {
            archiveFile.delete();
        }
    }

    @Test
    public void testAddFile() {
        String fileName = "tests/file.txt";
        archiver.addFile(fileName);

        List<String> files = archiver.getFiles();
        assertEquals(1, files.size());
        assertEquals(TEST_RESOURCE_DIRECTORY + fileName, files.get(0));
    }

    @Test
    public void testArchiveFiles() throws IOException {
        String fileName1 = "tests/file1.txt";
        String fileName2 = "tests/file2.txt";
        createTestFile(fileName1);
        createTestFile(fileName2);

        archiver.addFile(fileName1);
        archiver.addFile(fileName2);

        archiver.archiveFiles(TEST_ARCHIVE_NAME);

        File archiveFile = new File(TEST_RESOURCE_DIRECTORY + TEST_ARCHIVE_NAME);
        assertTrue(archiveFile.exists());

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(archiveFile))) {
            ZipEntry entry;
            int entryCount = 0;

            while ((entry = zipIn.getNextEntry()) != null) {
                entryCount++;

                String entryFileName = entry.getName();
                assertTrue(entryFileName.equals(TEST_RESOURCE_DIRECTORY + fileName1)
                        || entryFileName.equals(TEST_RESOURCE_DIRECTORY+ fileName2));

                byte[] buffer = new byte[1024];
                int bytesRead;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                while ((bytesRead = zipIn.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                String extractedContent = outputStream.toString();
                assertTrue(extractedContent.isEmpty());

                outputStream.close();
            }

            assertEquals(2, entryCount);
        }
    }

    private void createTestFile(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(TEST_RESOURCE_DIRECTORY + fileName);
        fos.close();
    }
}