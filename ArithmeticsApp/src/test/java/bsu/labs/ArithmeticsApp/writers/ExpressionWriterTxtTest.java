package bsu.labs.ArithmeticsApp.writers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ExpressionWriterTxtTest {
    private static final String TEST_FILE = "tests/writeTest.txt";
    private ExpressionWriterTxt writer;

    @BeforeEach
    public void setup() {
        writer = new ExpressionWriterTxt();
    }

    @AfterEach
    public void cleanup() throws IOException {
        File file = new File("src/main/resources/" + TEST_FILE);
        file.delete();
    }

    @Test
    void testWriteResult() throws IOException {
        List<String> result = Arrays.asList("3.0", "12.0", "5.0");

        writer.writeResult(result, TEST_FILE);
        File file = new File("src/main/resources/" + TEST_FILE);
        assertTrue(file.exists());
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/" + TEST_FILE));
        assertEquals(3, lines.size());
        assertEquals("3.0", lines.get(0));
        assertEquals("12.0", lines.get(1));
        assertEquals("5.0", lines.get(2));
    }

    @Test
    void testWriteResultWithEmptyResult() throws IOException {
        writer.writeResult(new ArrayList<String>(), TEST_FILE);
        File file = new File("src/main/resources/" + TEST_FILE);
        assertTrue(file.exists());
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/" + TEST_FILE));
        assertTrue(lines.isEmpty());
    }
}