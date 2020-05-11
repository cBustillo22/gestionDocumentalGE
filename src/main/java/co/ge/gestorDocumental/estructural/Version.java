package co.ge.gestorDocumental.estructural;

import java.util.UUID;

public class Version {

    private UUID uuid;
    private int version;
    private String base64File;
    private boolean esVersionMayor;
    private String documentoRaiz;

    public Version(UUID uuid, int version, String base64File, boolean esVersionMayor, String documentoRaiz) {
        this.uuid = uuid;
        this.version = version;
        this.base64File = base64File;
        this.esVersionMayor = esVersionMayor;
        this.documentoRaiz = documentoRaiz;
    }

    public Version(){}

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getBase64File() {
        return base64File;
    }

    public void setBase64File(String base64File) {
        this.base64File = base64File;
    }

    public boolean isEsVersionMayor() {
        return esVersionMayor;
    }

    public void setEsVersionMayor(boolean esVersionMayor) {
        this.esVersionMayor = esVersionMayor;
    }

    public String getDocumentoRaiz() {
        return documentoRaiz;
    }

    public void setDocumentoRaiz(String documentoRaiz) {
        this.documentoRaiz = documentoRaiz;
    }

    @Override
    public String toString() {
        return "Version{" +
                "uuid=" + uuid +
                ", version=" + version +
                ", base64File='" + base64File + '\'' +
                ", esVersionMayor=" + esVersionMayor +
                ", documentoRaiz='" + documentoRaiz + '\'' +
                '}';
    }
}
