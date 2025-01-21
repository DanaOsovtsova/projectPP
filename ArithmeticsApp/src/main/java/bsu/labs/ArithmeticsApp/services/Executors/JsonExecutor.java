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

@Service("json")
public class JsonExecutor implements ExecutorService {
    private final ExpressionWriter writer;
    private final FileEncryptor encryptor;
    private final Archiver archiver;
    private final Unarchiver unarchiver;
    private final ExpressionReader reader;
    private final ExpressionServiceCalculator calculator;

    @Autowired
    public JsonExecutor(@Qualifier("JsonWriter") ExpressionWriter writer,
                        FileEncryptor encryptor,
                        Archiver archiver,
                        Unarchiver unarchiver,
                        @Qualifier("JsonReader") ExpressionReader reader,
                        ExpressionServiceCalculator calculator) {
        this.writer = writer;
        this.encryptor = encryptor;
        this.archiver = archiver;
        this.unarchiver = unarchiver;
        this.reader = reader;
        this.calculator = calculator;
    }

    public List<String> execute(List<String> data) throws IOException {
        writer.writeResult(data, "inputs/input.json");

        encryptor.encryptFile("inputs/input.json", "encrypts/encryptedInputJson.bin");

        archiver.addFile("encrypts/encryptedInputJson.bin");
        archiver.archiveFiles("archives/InputJsonArchive.zip");
        archiver.clearArchive();

        unarchiver.extractArchive("archives/InputJsonArchive.zip", "unarchived");

        encryptor.decryptFile("unarchived/encryptedInputJson.bin", "decrypts/decryptedInputJson.json");

        List<String> readData = reader.read("decrypts/decryptedInputJson.json");
        List<String> resultData = calculator.calculateExpression(readData);

        writer.writeResult(resultData, "outputs/output.json");

        encryptor.encryptFile("outputs/output.json", "encrypts/encryptedOutputJson.bin");
        archiver.addFile("encrypts/encryptedOutputJson.bin");
        archiver.archiveFiles("archives/OutputJsonArchive.zip");
        archiver.clearArchive();

        return resultData;
    }

}
