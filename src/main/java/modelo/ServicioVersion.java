package modelo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;
import conexion.Conexion;
import estructural.Carpeta;
import estructural.Documento;
import estructural.Version;

import java.util.Date;
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
        String b64file = "b64test123";
        Version ver = new Version(UUID.randomUUID(), 1, b64file, true, doc1.getNombre());
        servicioVersion.agregar(ver, doc1);
    }

}
