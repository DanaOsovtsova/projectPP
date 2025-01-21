package bsu.labs.ArithmeticsApp.services.Executors;

import bsu.labs.ArithmeticsApp.archivers.Archiver;
import bsu.labs.ArithmeticsApp.archivers.Unarchiver;
import bsu.labs.ArithmeticsApp.encryptor.FileEncryptor;
import bsu.labs.ArithmeticsApp.readers.ExpressionReader;
import bsu.labs.ArithmeticsApp.services.calculator.ExpressionServiceCalculator;
import bsu.labs.ArithmeticsApp.writers.ExpressionWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("xml")
public class XmlExecutor implements ExecutorService {
    private final ExpressionWriter writer;
    private final FileEncryptor encryptor;
    private final Archiver archiver;
    private final Unarchiver unarchiver;
    private final ExpressionReader reader;
    private final ExpressionServiceCalculator calculator;

    @Autowired
    public XmlExecutor(@Qualifier("XmlWriter") ExpressionWriter writer,
                       FileEncryptor encryptor,
                       Archiver archiver,
                       Unarchiver unarchiver,
                       @Qualifier("XmlReader") ExpressionReader reader,
                       ExpressionServiceCalculator calculator) {
        this.writer = writer;
        this.encryptor = encryptor;
        this.archiver = archiver;
        this.unarchiver = unarchiver;
        this.reader = reader;
        this.calculator = calculator;
    }

    public List<String> execute(List<String> data) throws IOException {
        writer.writeResult(data, "inputs/input.xml");

        encryptor.encryptFile("inputs/input.xml", "encrypts/encryptedInputXml.bin");

        archiver.addFile("encrypts/encryptedInputXml.bin");
        archiver.archiveFiles("archives/InputXmlArchive.zip");
        archiver.clearArchive();

        unarchiver.extractArchive("archives/InputXmlArchive.zip", "unarchived");

        encryptor.decryptFile("unarchived/encryptedInputXml.bin", "decrypts/decryptedInputXml.xml");

        List<String> readData = reader.read("decrypts/decryptedInputXml.xml");
        List<String> resultData = calculator.calculateExpression(readData);

        writer.writeResult(resultData, "outputs/output.xml");

        encryptor.encryptFile("outputs/output.xml", "encrypts/encryptedOutputXml.bin");
        archiver.addFile("encrypts/encryptedOutputXml.bin");
        archiver.archiveFiles("archives/OutputXmlArchive.zip");
        archiver.clearArchive();

        return resultData;
    }
}
