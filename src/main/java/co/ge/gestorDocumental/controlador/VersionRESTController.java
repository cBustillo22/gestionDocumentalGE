package co.ge.gestorDocumental.controlador;

import co.ge.gestorDocumental.estructural.Documento;
import co.ge.gestorDocumental.estructural.Version;
import co.ge.gestorDocumental.modelo.ServicioDocumento;
import co.ge.gestorDocumental.modelo.ServicioVersion;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/v1/version")
public class VersionRESTController {

    private ServicioVersion servicioVersion;
    private ServicioDocumento servicioDocumento;

    public VersionRESTController() {
        this.servicioVersion = ServicioVersion.getInstancia();
        this.servicioDocumento = ServicioDocumento.getInstancia();
    }

    @GetMapping(path = "/{nombreDocumento}")
    public List<Version> listar(@PathVariable("nombreDocumento") String nombreDocumento){
        Documento doc = servicioDocumento.buscar(nombreDocumento);
        return servicioVersion.listar(doc);
    }

    @GetMapping(path = "/v/{nombreDocumento}/{version}")
    public Version buscarVersionDocumento(@PathVariable("version") int version,@PathVariable("nombreDocumento") String nombreDocumento){
        Documento doc = servicioDocumento.buscar(nombreDocumento);
        Version ver = new Version();
        ver.setDocumentoRaiz(nombreDocumento);
        ver.setVersion(version);
        return servicioVersion.buscarPorVersion(ver, doc);
    }

    @PostMapping
    public boolean agregar(@RequestBody Version version){
        Documento doc = servicioDocumento.buscar(version.getDocumentoRaiz());
        return servicioVersion.agregar(version, doc);
    }

    @GetMapping(path = "/{nombreDocumento}/versionMayor")
    public Version obtenerVersionMayor(@PathVariable("nombreDocumento") String nombreDocumento){
        Documento doc = servicioDocumento.buscar(nombreDocumento);
        return servicioVersion.obtenerVersionMayor(doc);
    }

    @DeleteMapping(path = "/{nombreDocumento}/{version}")
    public boolean eliminar(@PathVariable("version") int version, @PathVariable("nombreDocumento") String nombreDocumento){
        Documento doc = servicioDocumento.buscar(nombreDocumento);
        Version ver = new Version();
        ver.setVersion(version);
        ver.setDocumentoRaiz(doc.getNombre());
        return servicioVersion.eliminar(ver, doc);
    }

    @PutMapping
    public boolean actualizar(@RequestBody Version version){
        Documento doc = servicioDocumento.buscar(version.getDocumentoRaiz());
        return servicioVersion.actualizar(version, doc);
    }
}
