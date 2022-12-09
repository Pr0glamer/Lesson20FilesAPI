import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FilesApiWork {

    private final String directory;
    private final String fileName;

    public void createDirectory(){
        File theDir = new File(directory);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
    }

    public void downloadFile(URL url, String outputFileName) throws IOException
    {
        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in);
             FileOutputStream fos = new FileOutputStream(outputFileName)) {
             fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }

    public void createFile(){
        Path newFilePath = Paths.get(directory + "/" + fileName);
        try {
            if(!Files.exists(newFilePath)) {
                Files.createFile(newFilePath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fillFileWithData(String name, int age, String sex) {
        Path newFilePath = Paths.get(directory + "/" + fileName);

        if (Files.exists(newFilePath)) {
            String resultString = name + ";" + age + ";" + sex + ";" + "\n";
            try {
                Files.write(newFilePath, resultString.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);

            }

        }
    }

    public static void main(String[] args) {
        FilesApiWork filesApiWork = new FilesApiWork("output", "result.csv");
        filesApiWork.createDirectory();
        filesApiWork.createFile();
        filesApiWork.fillFileWithData("Vasya", 12, "MALE");
        filesApiWork.fillFileWithData("Petya", 42, "MALE");
        try {
            String fileName = "downloaded.png";
            filesApiWork.downloadFile(new URL("https://itc.ua/wp-content/uploads/2022/12/ukfdyfz.png"), fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public FilesApiWork(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
    }
}
