package exercise;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

class App {

    // BEGIN
    private static Path getFullPath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    public static CompletableFuture<String> unionFiles(String source1, String source2, String dest) {

        CompletableFuture<String> futureContent1 = CompletableFuture.supplyAsync(() -> {
            String content = null;
            try {
                content = Files.readString(getFullPath(source1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content;
        });

        CompletableFuture<String> futureContent2 = CompletableFuture.supplyAsync(() -> {
            String content = null;
            try {
                content = Files.readString(Paths.get(source2));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content;
        });

        CompletableFuture<String> futureResultContent = futureContent1.thenCombine(futureContent2,
                (file1Content, file2Content) -> {
            Path destPath = Paths.get(dest);
            String resultContent = file1Content + file2Content;
            try {
                Files.writeString(destPath, resultContent, StandardOpenOption.CREATE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return resultContent;
        }).exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return null;
        });

        return futureResultContent;
    }
    // END

    public static void main(String[] args) throws Exception {
        // BEGIN
        CompletableFuture<String> result = unionFiles("src/main/resources/file1.txt",
                "src/main/resources/file2.txt", "src/main/resources/result.txt");
        System.out.println(result.get());
        System.out.println(getDirectorySize(".gitignore").get());
        // END
    }

    public static CompletableFuture<Long> getDirectorySize(String path) {
        return CompletableFuture.supplyAsync(() -> {
            Long size = 0L;
            try {
                size = Files.walk(getFullPath(path), 1)
                        .filter(p -> Files.isRegularFile(p))
                        .mapToLong(p -> {
                            try {
                                return Files.size(p);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .sum();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return size;
        }).exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return null;
        });
    }

}
