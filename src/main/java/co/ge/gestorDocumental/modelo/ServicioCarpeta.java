package co.ge.gestorDocumental.modelo;

import co.ge.gestorDocumental.conexion.Conexion;
import co.ge.gestorDocumental.estructural.Carpeta;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;


import java.util.ArrayList;
import java.util.List;

public class ServicioCarpeta {

    private Conexion conexion;
    private static ServicioCarpeta instancia;

    public ServicioCarpeta() {
        this.conexion = Conexion.getInstance();
        this.instancia = this;
    }

    public static ServicioCarpeta getInstance(){
        if (instancia==null){
            instancia=new ServicioCarpeta();
        }
        return instancia;
    }

    public Carpeta agregar(Carpeta carpeta){
        boolean respuesta = false;
        try {
            ApiFuture<WriteResult> future = conexion.getDb().collection("carpetas").document(carpeta.getNombre()).set(carpeta);
            System.out.println("Update time : " + future.get().getUpdateTime());
            return carpeta;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean eliminar(String carpeta){
        boolean resultado = false;
        try {
            ApiFuture<WriteResult> writeResult = conexion.getDb().collection("carpetas").document(carpeta).delete();
            System.out.println("Update time : " + writeResult.get().getUpdateTime());
            resultado = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean actualizar(Carpeta carpeta){
        boolean respuesta = false;
        try {
            ApiFuture<WriteResult> future = conexion.getDb().collection("carpetas").document(carpeta.getNombre()).set(carpeta);
            System.out.println("Update time : " + future.get().getUpdateTime());
            respuesta=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    public Carpeta buscarPorNombre(String carpeta){
        Carpeta carpetaEncontrada = null;
        try {
            DocumentReference docRef = conexion.getDb().collection("carpetas").document(carpeta);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                carpetaEncontrada = new Carpeta(document.getString("nombre"), document.getDate("fechaCreacion"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return carpetaEncontrada;
    }

    public ArrayList<Carpeta> listar(){
        ArrayList<Carpeta> respuesta = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> query = conexion.getDb().collection("carpetas").get();
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Carpeta carpeta = new Carpeta(document.getString("nombre"), document.getDate("fechaCreacion"));
                respuesta.add(carpeta);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

   /*public static void main(String[] args) {
        ServicioCarpeta servicioCarpeta = new ServicioCarpeta();
        //servicioCarpeta.agregar(new Carpeta("Recibos", new Date()));
        //servicioCarpeta.eliminar("Recibos");
        //servicioCarpeta.actualizar(new Carpeta("Facturas", new Date()));
        //Carpeta c = servicioCarpeta.buscarPorNombre("Facturas");
        servicioCarpeta.listar();
        System.out.println(servicioCarpeta.listar());
    }*/

}
