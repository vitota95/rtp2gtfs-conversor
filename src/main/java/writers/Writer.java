package writers;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by javig on 14/07/2017.
 */

public class Writer  {

    private static final String GTFSName = "GTFS.zip";
    private static Writer instance;

    public Writer() {
    }

    public static Writer getInstance() throws  FileNotFoundException{
        if(instance == null){
            instance = new Writer();
        }
        return instance;
    }

    //Source: https://stackoverflow.com/questions/2223434/appending-files-to-a-zip-file-with-java
    public void write(String fileName, ArrayList<String> entities, String outputDirectory) throws IOException {

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        Path path = Paths.get(outputDirectory + GTFSName);
        URI uri = URI.create("jar:" + path.toUri());
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("\n", entities));

        try (FileSystem fs = FileSystems.newFileSystem(uri, env)) {
            Path nf = fs.getPath(fileName);
            try (BufferedWriter writer = Files.newBufferedWriter(nf, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
                writer.write(sb.toString());
            }
        }
    }
}
