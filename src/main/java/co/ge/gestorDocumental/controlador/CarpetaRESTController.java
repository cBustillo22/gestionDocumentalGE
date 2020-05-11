package co.ge.gestorDocumental.controlador;

import co.ge.gestorDocumental.estructural.Carpeta;
import co.ge.gestorDocumental.estructural.Documento;
import co.ge.gestorDocumental.modelo.ServicioCarpeta;
import co.ge.gestorDocumental.modelo.ServicioDocumento;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RequestMapping("api/v1/carpeta")
@RestController
public class CarpetaRESTController {

    private ServicioCarpeta servicioCarpeta;
    private ServicioDocumento servicioDocumento;

    public CarpetaRESTController() {
        this.servicioCarpeta = ServicioCarpeta.getInstance();
        this.servicioDocumento = ServicioDocumento.getInstancia();
    }

    @GetMapping
    public ArrayList<Carpeta> listar(){
        return servicioCarpeta.listar();
    }

    @PostMapping
    public boolean agregar(@RequestBody Carpeta carpeta){
        return servicioCarpeta.agregar(carpeta);
    }

    @DeleteMapping(path = "/{carpeta}")
    public boolean eliminar(@PathVariable("carpeta") String carpeta){
        return servicioCarpeta.eliminar(carpeta);
    }

    @GetMapping(path = "/{carpeta}")
    public Carpeta buscarPorNombre(@PathVariable("carpeta") String carpeta){
        return servicioCarpeta.buscarPorNombre(carpeta);
    }

    @PutMapping
    public boolean actualizar(@RequestBody Carpeta carpeta){
        return servicioCarpeta.actualizar(carpeta);
    }

    @GetMapping(path = "/{carpeta}/documentos")
    public List<Documento> listarDocumentosCarpeta(@PathVariable String carpeta){
        return servicioDocumento.listarPorCarpeta(carpeta);
    }

}
