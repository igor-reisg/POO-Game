package modelos.Cartas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

public class CoringaReader {
    public static CoringaDetails CoringaRead(String caminhoArquivo) throws IOException {
        Gson gson = new GsonBuilder().create();

        try (FileReader reader = new FileReader(caminhoArquivo)) {
            return gson.fromJson(reader, CoringaDetails.class);
        }
    }
}
