package bsu.labs.ArithmeticsApp.writers;
import bsu.labs.ArithmeticsApp.readers.ExpressionReader;
import bsu.labs.ArithmeticsApp.readers.ExpressionReaderXml;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
class ExpressionWriterXmlTest {
    private static final String TEST_FILE = "tests/writeTest.xml";
    private ExpressionWriterXml writer;

    @BeforeEach
    public void setup() {
        writer = new ExpressionWriterXml();
    }

    @AfterEach
    public void cleanup() throws Exception {
        File file = new File("src/main/resources/" + TEST_FILE);
        file.delete();
    }

    @Test
    void testWriteResult() throws Exception {
        List<String> result = Arrays.asList("3.0", "12.0", "5.0");
        writer.writeResult(result, TEST_FILE);
        File file = new File("src/main/resources/" + TEST_FILE);
        assertTrue(file.exists());
        ExpressionReader reader = new ExpressionReaderXml();
        List<String> readResult = reader.read(TEST_FILE);
        assertEquals(3, result.size());
        assertEquals(readResult.get(0), result.get(0).toString());
        assertEquals(readResult.get(1), result.get(1).toString());
        assertEquals(readResult.get(2), result.get(2).toString());
    }
}