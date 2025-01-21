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

@Service("txt")
public class TxtExecutor implements ExecutorService {
    private final ExpressionWriter writer;
    private final FileEncryptor encryptor;
    private final Archiver archiver;
    private final Unarchiver unarchiver;
    private final ExpressionReader reader;
    private final ExpressionServiceCalculator calculator;
    @Autowired
    public TxtExecutor(@Qualifier("TxtWriter") ExpressionWriter writer,
                       FileEncryptor encryptor,
                       Archiver archiver,
                       Unarchiver unarchiver,
                       @Qualifier("TxtReader") ExpressionReader reader,
                       ExpressionServiceCalculator calculator) {
        this.writer = writer;
        this.encryptor = encryptor;
        this.archiver = archiver;
        this.unarchiver = unarchiver;
        this.reader = reader;
        this.calculator = calculator;
    }

    public List<String> execute(List<String> data) throws IOException {
        writer.writeResult(data, "inputs/input.txt");

        encryptor.encryptFile("inputs/input.txt", "encrypts/encryptedInputTxt.bin");

        archiver.addFile("encrypts/encryptedInputTxt.bin");
        archiver.archiveFiles("archives/InputTxtArchive.zip");
        archiver.clearArchive();

        unarchiver.extractArchive("archives/InputTxtArchive.zip", "unarchived");

        encryptor.decryptFile("unarchived/encryptedInputTxt.bin", "decrypts/decryptedInputTxt.txt");

        List<String> readData = reader.read("decrypts/decryptedInputTxt.txt");
        List<String> resultData = calculator.calculateExpression(readData);

        writer.writeResult(resultData, "outputs/output.txt");

        encryptor.encryptFile("outputs/output.txt", "encrypts/encryptedOutputTxt.bin");
        archiver.addFile("encrypts/encryptedOutputTxt.bin");
        archiver.archiveFiles("archives/OutputTxtArchive.zip");
        archiver.clearArchive();

        return resultData;
    }
}
