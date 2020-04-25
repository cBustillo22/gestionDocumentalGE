package conexion;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;

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
        Firestore db = null;
        try {
            System.out.println(Conexion.class.getResource("/credenciales_firestore.json").getPath());
            FileInputStream serviceAccount =
                    new FileInputStream(Conexion.class.getResource("/credenciales_firestore.json").getPath());

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://gestiondocumental-7471b.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return db;
    }

}
