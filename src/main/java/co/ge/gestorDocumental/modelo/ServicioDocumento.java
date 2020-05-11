package co.ge.gestorDocumental.modelo;

import co.ge.gestorDocumental.estructural.Carpeta;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import co.ge.gestorDocumental.conexion.Conexion;
import co.ge.gestorDocumental.estructural.Documento;

import java.util.ArrayList;
import java.util.List;

public class ServicioDocumento {

    private Conexion conexion;
    private static ServicioDocumento instancia;
    private ServicioCarpeta servicioCarpeta;

    public ServicioDocumento() {
        this.conexion = Conexion.getInstance();
        this.servicioCarpeta = ServicioCarpeta.getInstance();
        this.instancia = this;
    }

    public static ServicioDocumento getInstancia(){
        if (instancia==null){
            instancia= new ServicioDocumento();
        }
        return instancia;
    }

    public boolean agregar(Documento documento){
        boolean respuesta = false;
        try {
            ApiFuture<WriteResult> future = conexion.getDb().collection("carpetas")
                    .document(documento.getCarpetaRaiz())
                    .collection("archivos")
                    .document(documento.getNombre()).set(documento);
            System.out.println("Update time : " + future.get().getUpdateTime());
            respuesta=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    public boolean eliminar(Documento documento){
        boolean resultado = false;
        try {
            ApiFuture<WriteResult> writeResult = conexion.getDb().collection("carpetas")
                    .document(documento.getCarpetaRaiz())
                    .collection("archivos")
                    .document(documento.getNombre()).delete();
            System.out.println("Update time : " + writeResult.get().getUpdateTime());
            resultado = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean actualizar(Documento documento){
        boolean respuesta = false;
        try {
            ApiFuture<WriteResult> future = conexion.getDb().collection("carpetas")
                    .document(documento.getCarpetaRaiz())
                    .collection("archivos")
                    .document(documento.getNombre()).set(documento);
            System.out.println("Update time : " + future.get().getUpdateTime());
            respuesta=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    public Documento buscarPorNombre(Documento documento){
        Documento documentoEncontrado = null;
        try {
            DocumentReference docRef = conexion.getDb().collection("carpetas")
                    .document(documento.getCarpetaRaiz())
                    .collection("archivos")
                    .document(documento.getNombre());
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                documentoEncontrado = new Documento(document.getString("carpetaRaiz"),
                        document.getString("nombre"),
                        document.getString("autor"),
                        document.getDouble("tamanio"),
                        document.getString("tipo"),
                        document.getString("descripcion"),
                        document.getDate("fechaCreacion"),
                        document.getString("estado"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return documentoEncontrado;
    }

    public ArrayList<Documento> listarPorCarpeta(String carpeta){
        ArrayList<Documento> respuesta = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> query = conexion.getDb().collection("carpetas")
                    .document(carpeta)
                    .collection("archivos")
                    .get();
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Documento documento = new Documento(document.getString("carpetaRaiz"),
                        document.getString("nombre"),
                        document.getString("autor"),
                        document.getDouble("tamanio"),
                        document.getString("tipo"),
                        document.getString("descripcion"),
                        document.getDate("fechaCreacion"),
                        document.getString("estado"));
                respuesta.add(documento);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    public Documento buscar(String nombreDocumento){
        List<Carpeta> carpetas = servicioCarpeta.listar();
        Documento docEncontrado = null;
        for (Carpeta carpeta:carpetas) {
            Documento doc = new Documento();
            doc.setCarpetaRaiz(carpeta.getNombre());
            doc.setNombre(nombreDocumento);
            docEncontrado = buscarPorNombre(doc);
            if (docEncontrado!=null){
                break;
            }
        }
        return docEncontrado;
    }

    public List<Documento> listar(){
        List<Carpeta> carpetas = servicioCarpeta.listar();
        List<Documento> documentos = new ArrayList<Documento>();
        for (Carpeta carpeta : carpetas) {
            List<Documento> aux = listarPorCarpeta(carpeta.getNombre());
            for (Documento doc:aux ) {
                documentos.add(doc);
            }
        }
        return documentos;
    }

    /*public static void main(String[] args) {
        ServicioDocumento servicioDocumento = new ServicioDocumento();
        ServicioCarpeta servicioCarpeta = new ServicioCarpeta();
        Carpeta carpeta = servicioCarpeta.buscarPorNombre("Facturas");
        //Documento doc = new Documento(carpeta.getNombre(), "facturaAbril.pdf","administrador2",742.15,"pdf", "factura del servicio del agua mes de abril", new Date(), "ACTIVO");
        //Documento doc3 = servicioDocumento.buscarPorNombre(doc);
        ArrayList<Documento> doc2 = servicioDocumento.listar("Facturas");
        System.out.println(doc2);
    }*/
}
