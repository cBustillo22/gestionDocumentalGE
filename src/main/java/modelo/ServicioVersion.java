package modelo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import conexion.Conexion;
import estructural.Carpeta;
import estructural.Documento;
import estructural.Version;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ServicioVersion {

    private Conexion conexion;
    private static ServicioVersion instancia;

    public ServicioVersion() {
        this.conexion = Conexion.getInstance();
        this.instancia = this;
    }

    public ServicioVersion getInstancia(){
        if(instancia==null){
            instancia = new ServicioVersion();
        }
        return instancia;
    }

    public boolean agregar(Version versionDocumento, Documento documentoRaiz){
        boolean respuesta = false;
        try {
            ApiFuture<WriteResult> future = conexion.getDb().collection("carpetas")
                    .document(documentoRaiz.getCarpetaRaiz())
                    .collection("archivos")
                    .document(versionDocumento.getDocumentoRaiz())
                    .collection("versiones")
                    .document(Integer.toString(versionDocumento.getVersion()))
                    .set(versionDocumento);
            System.out.println("Update time : " + future.get().getUpdateTime());
            respuesta=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    public boolean eliminar(Version versionDocuemnto, Documento documentoRaiz){
        boolean resultado = false;
        try {
            ApiFuture<WriteResult> writeResult = conexion.getDb().collection("carpetas")
                    .document(documentoRaiz.getCarpetaRaiz())
                    .collection("archivos")
                    .document(versionDocuemnto.getDocumentoRaiz())
                    .collection("versiones")
                    .document(Integer.toString(versionDocuemnto.getVersion()))
                    .delete();
            System.out.println("Update time : " + writeResult.get().getUpdateTime());
            resultado = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean actualizar(Version versionDocumento, Documento documentoRaiz){
        boolean respuesta = false;
        try {
            ApiFuture<WriteResult> future = conexion.getDb().collection("carpetas")
                    .document(documentoRaiz.getCarpetaRaiz())
                    .collection("archivos")
                    .document(versionDocumento.getDocumentoRaiz())
                    .collection("versiones")
                    .document(Integer.toString(versionDocumento.getVersion()))
                    .set(versionDocumento);
            System.out.println("Update time : " + future.get().getUpdateTime());
            respuesta=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }

    public Version buscarPorVersion(Version versionDocumento, Documento documentoRaiz){
        Version versionEncontrada = null;
        try {
            DocumentReference docRef = conexion.getDb().collection("carpetas")
                    .document(documentoRaiz.getCarpetaRaiz())
                    .collection("archivos")
                    .document(versionDocumento.getDocumentoRaiz())
                    .collection("versiones")
                    .document(Integer.toString(versionDocumento.getVersion()));
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                versionEncontrada = new Version(null,
                        Integer.parseInt(document.get("version").toString()),
                        document.getString("base64File"),
                        document.getBoolean("esVersionMayor"),
                        document.getString("documentoRaiz")
                        );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return versionEncontrada;
    }

    public ArrayList<Version> listar(Documento documento){
        ArrayList<Version> respuesta = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> query = conexion.getDb().collection("carpetas")
                    .document(documento.getCarpetaRaiz())
                    .collection("archivos")
                    .document(documento.getNombre())
                    .collection("versiones")
                    .get();
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Version versionDocumento = new Version(null,
                        Integer.parseInt(document.get("version").toString()),
                        document.getString("base64File"),
                        document.getBoolean("esVersionMayor"),
                        document.getString("documentoRaiz")
                );
                respuesta.add(versionDocumento);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }


    public static void main(String[] args) {
        ServicioDocumento servicioDocumento = new ServicioDocumento();
        ServicioCarpeta servicioCarpeta = new ServicioCarpeta();
        ServicioVersion servicioVersion = new ServicioVersion();
        Carpeta carpeta = servicioCarpeta.buscarPorNombre("Facturas");
        Documento doc = new Documento();
        doc.setNombre("facturaAbril.pdf");
        doc.setCarpetaRaiz(carpeta.getNombre());
        Documento doc1 = servicioDocumento.buscarPorNombre(doc);
        //Documento doc = new Documento(carpeta, "facturaAbril.pdf","administrador2",742.15,"pdf", "factura del servicio del agua mes de abril", new Date(), "ACTIVO");
        String b64file = JOptionPane.showInputDialog("a");
        Version ver = new Version(UUID.randomUUID(), 2, b64file, true, doc1.getNombre());
        //Version ver1 = servicioVersion.buscarPorVersion(ver, doc1);
        ArrayList<Version> lista = servicioVersion.listar(doc1);
        System.out.println(lista);
    }

}
