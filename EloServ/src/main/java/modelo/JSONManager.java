package modelo;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONManager {
    private static final String PATH = "EuskadiLatLon.json"; 

    public static String[] obtenerInfoCentro(int idCentro) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(PATH)));
            JSONObject jsonPrincipal = new JSONObject(contenido);
            JSONArray centros = jsonPrincipal.getJSONArray("CENTROS");

            for (int i = 0; i < centros.length(); i++) {
                JSONObject c = centros.getJSONObject(i);
                if (c.getInt("CCEN") == idCentro) {
                    return new String[]{ c.getString("NOM"), c.getString("DMUNIC") };
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{"Desconocido", "Desconocido"};
    }
}