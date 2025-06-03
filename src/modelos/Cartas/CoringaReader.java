package modelos.Cartas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class CoringaReader {
    public static CoringaDetails CoringaRead(String caminhoArquivo) throws IOException {
        Gson gson = new GsonBuilder().create();

        InputStream inputStream = CoringaReader.class.getResourceAsStream(caminhoArquivo);
        if (inputStream == null) {
            throw new FileNotFoundException("Arquivo não encontrado no classpath: " + caminhoArquivo);
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, CoringaDetails.class);
        }
    }
}
