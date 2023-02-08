import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DownloadFile {

    public static void main(String[] args) throws Exception {
        String url = "https://www.example.com/textfile.txt";

        CompletableFuture<String> fileContentFuture = CompletableFuture.supplyAsync(() -> {
            try {
                URL fileURL = new URL(url);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileURL.openStream()));
                return reader.lines().collect(Collectors.joining("\n"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        fileContentFuture.thenAccept(fileContent -> {
            System.out.println("Contenido del archivo:");
            System.out.println(fileContent);
        });
    }
}
