import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.CompletableFuture;

public class DownloadFile {
    public static void main(String[] args) throws Exception {
        String fileUrl = "https://www.forta.es/wp-prue-123.txt";
        String fileName = "src/wp-prue-123.txt";

        CompletableFuture<Void> downloadFile = CompletableFuture.runAsync(() -> {
            try {
                URL website = new URL(fileUrl);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        CompletableFuture<Void> openFile = downloadFile.thenRun(() -> {
            try {
                String[] command = { "notepad.exe", fileName };
                ProcessBuilder builder = new ProcessBuilder(command);
                builder.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        openFile.get();
    }
}
