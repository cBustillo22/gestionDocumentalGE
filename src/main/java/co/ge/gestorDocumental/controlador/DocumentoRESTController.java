package co.ge.gestorDocumental.controlador;

import co.ge.gestorDocumental.estructural.Documento;
import co.ge.gestorDocumental.modelo.ServicioCarpeta;
import co.ge.gestorDocumental.modelo.ServicioDocumento;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/v1/documento")
public class DocumentoRESTController {

    private ServicioDocumento servicioDocumento;
    private ServicioCarpeta servicioCarpeta;

    public DocumentoRESTController() {
        this.servicioDocumento = ServicioDocumento.getInstancia();
        this.servicioCarpeta = ServicioCarpeta.getInstance();
    }

    //pendiente validar
    @GetMapping(path = "/{carpeta}/{nombreDocumento}")
    public Documento buscarPorNombreEnCarpeta(@PathVariable("carpeta") String carpeta,@PathVariable("nombreDocumento") String nombreDocumento) {
        Documento doc = new Documento();
        doc.setCarpetaRaiz(carpeta);
        doc.setNombre(nombreDocumento);
        return servicioDocumento.buscarPorNombre(doc);
    }

    @PostMapping
    public ResponseEntity<Documento> añadir(@RequestBody Documento documento){
        return ResponseEntity.ok().body(servicioDocumento.agregar(documento));
    }

    @PutMapping
    public boolean actualizar(@RequestBody Documento documento){
        return servicioDocumento.actualizar(documento);
    }

    @DeleteMapping(path = "/{carpeta}/{nombreDocumento}")
    public boolean eliminarDesdeCarpeta(@PathVariable String nombreDocumento,@PathVariable String carpeta){
        Documento doc =  new Documento();
        doc.setCarpetaRaiz(carpeta);
        doc.setNombre(nombreDocumento);
        return servicioDocumento.eliminar(doc);
    }

    @DeleteMapping(path = "/{nombreDocumento}")
    public boolean eliminar(@PathVariable String nombreDocumento){
        Documento doc =  servicioDocumento.buscar(nombreDocumento);
        return servicioDocumento.eliminar(doc);
    }


    @GetMapping
    public List<Documento> listar(){
        return servicioDocumento.listar();
    }

    @GetMapping(path = "/{nombreDocumento}")
    public Documento buscarPorNombre(@PathVariable("nombreDocumento") String nombreDocumento) {
        return servicioDocumento.buscar(nombreDocumento);
    }
}
