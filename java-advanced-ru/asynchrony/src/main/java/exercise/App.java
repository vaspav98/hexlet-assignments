package exercise;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

class App {

    // BEGIN
    public static CompletableFuture<String> unionFiles(String filePath1, String filePath2, String resultFilePath) {

        CompletableFuture<String> futureFile1Content = CompletableFuture.supplyAsync(() -> {
            String content = null;
            try {
                content = Files.readString(Paths.get(filePath1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content;
        }).exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return null;
        });

        CompletableFuture<String> futureFile2Content = CompletableFuture.supplyAsync(() -> {
            String content = null;
            try {
                content = Files.readString(Paths.get(filePath2));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content;
        }).exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return null;
        });

        CompletableFuture<String> futureResultFile = futureFile1Content.thenCombine(futureFile2Content,
                (file1Content, file2Content) -> {
            Path resultFile = Paths.get(resultFilePath);
            String resultContent = file1Content + file2Content;
            try {
                if (!Files.exists(resultFile)) {
                    Files.createFile(resultFile);
                }
                Files.writeString(resultFile, resultContent, StandardOpenOption.WRITE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return resultContent;
        }).exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return null;
        });

        return futureResultFile;
    }
    // END

    public static void main(String[] args) throws Exception {
        // BEGIN
        CompletableFuture<String> result = unionFiles("src/main/resources/file1.txt",
                "src/main/resources/file2.txt", "src/main/resources/result.txt");
        System.out.println(result.get());
        // END
    }
}

