import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class FilesApiWorkTest {

    @Test
    public void shouldCreateFileInOutputFolder() {

        String directory = "output";
        String fileName = "result.csv";
        FilesApiWork filesApiWork = new FilesApiWork(directory, fileName);

        File theFile = new File(directory + "/" + fileName);
        if (theFile.exists()){
            theFile.delete();
        }
        filesApiWork.createFile();
        assertTrue(theFile.exists());

    }

    @Test
    public void shouldCreateDirectoryWithOutputName() {

        String directory = "output";
        String fileName = "result.csv";
        FilesApiWork filesApiWork = new FilesApiWork(directory, fileName);

        File theDir = new File(directory);
        if (theDir.exists()) {
            theDir.delete();
        }

        filesApiWork.createDirectory();

        assertTrue(theDir.exists());

    }

    @Test
    public void shouldWriteCSVDataToFile() throws FileNotFoundException {
        String directory = "output";
        String fileName  = "result.csv";
        FilesApiWork filesApiWork = new FilesApiWork(directory, fileName);
        filesApiWork.createDirectory();
        filesApiWork.createFile();
        filesApiWork.fillFileWithData("Vasya", 12, "MALE");
        filesApiWork.fillFileWithData("Petya", 42, "MALE");

        Path newFilePath = Paths.get(directory + "/" + fileName);

        if (!Files.exists(newFilePath)) {
            throw new FileNotFoundException();
        }
        List<String> lines;
        try {
            lines = Files.readAllLines(newFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(lines.get(0).trim(), "Vasya;12;MALE;");

    }




    public void shouldDownloadFileFromNet() {

        FilesApiWork filesApiWork = new FilesApiWork("", "");
        try {
            String fileName = "downloaded.png";
            filesApiWork.downloadFile(new URL("https://itc.ua/wp-content/uploads/2022/12/ukfdyfz.png"), fileName);

            File theFile = new File(fileName);
            assertTrue(theFile.exists());

            assertEquals("png", getFileExtension(theFile.getAbsolutePath()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index == -1? null : fileName.substring(index);
    }

}
