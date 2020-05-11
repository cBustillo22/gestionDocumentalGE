package co.ge.gestorDocumental.conexion;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class Conexion {
    private Firestore db;
    private static Conexion conexion;

    public Conexion(){
        this.db = getDataBase();
        this.conexion = this;
    }

    public Firestore getDb() {
        return db;
    }

    public static Conexion getInstance(){
        if(conexion==null){
            conexion = new Conexion();
        }
        return conexion;
    }

    private static Firestore getDataBase() {
        String configFilePath = "/credenciales_firestore.json";
        String dataBaseURL = "https://gestiondocumental-7471b.firebaseio.com";
        Firestore db = null;
        try {
            System.out.println(Conexion.class.getResource(configFilePath).getPath());
            FileInputStream serviceAccount =
                    new FileInputStream(Conexion.class.getResource(configFilePath).getPath());

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(dataBaseURL)
                    .build();

            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return db;
    }

}
