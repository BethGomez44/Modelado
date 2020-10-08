package main.lib;

/**
 * Clase que nos ayudara a modelar nuestro objeto Lugar para obtener la
 * información necesaria para nuestro programa.
 */
public class Lugar {
    private final String PAIS_LOCAL = "México";
    String nombre, coor_lat, coor_long, clima, temp, pais;

    /**
     * Constructor por omisión
     */
    public Lugar() {
        nombre = "default";
        pais = "default";
        coor_lat = " ";
        coor_long = " ";
        clima = " ";
        temp = " ";
    }

    /**
     * Constructor por parámetros
     * 
     * @param nom       - nombre del lugar
     * @param pais      - pais del lugar
     * @param coor_lat  - coordenada de la latitud del lugar
     * @param coor_long - coordenada de la longitud del lugar
     * @param clim      - clima del lugar
     * @param temp      - temperatura del lugar
     */
    public Lugar(String nom, String pais, String coor_lat, String coor_long, String clim, String temp) {

        this.nombre = nom;
        this.pais = pais;
        this.coor_lat = coor_lat;
        this.coor_long = coor_long;
        this.clima = clim;
        this.temp = temp;

    }

    /**
     * Método que asigna un nombre al lugar.
     * 
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que asigna un pais al lugar.
     * 
     * @param nombre
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Método que asigna la coordenada de la latitud al lugar
     * 
     * @param coor_lat
     */
    public void setCoorLat(String coor_lat) {
        this.coor_lat = coor_lat;
    }

    /**
     * Método que asigna la coordenada de la longitud al lugar
     * 
     * @param coor_long
     */
    public void setCoorLong(String coor_long) {
        this.coor_long = coor_long;
    }

    /**
     * Método que asigna las coordenadas al lugar
     * 
     * @param coor_lat
     * @param coor_long
     */
    public void setCoordenadas(String coor_lat, String coor_long) {
        this.coor_lat = coor_lat;
        this.coor_long = coor_long;
    }

    /**
     * Método que asigna la temperatura al lugar
     * 
     * @param temp
     */
    public void setTemperatura(String temp) {
        this.temp = temp;
    }

    /**
     * Método que asigna el clima al lugar
     * 
     * @param clima
     */
    public void setClima(String clima) {
        this.clima = clima;
    }

    /**
     * Método que obtiene el nombre del lugar
     * 
     * @return nombre
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Método que obtiene el nombre del lugar
     * 
     * @return nombre
     */
    public String getPais() {
        return this.pais;
    }

    /**
     * Método que obtiene la coordenada de la latitud de un lugar
     * 
     * @return coordenada de la latitud
     */
    public String getCoorLat() {
        return this.coor_lat;
    }

    /**
     * Método que obtiene la coordenada de la longitud de un lugar
     * 
     * @return coordenada de longitud
     */
    public String getCoorLong() {
        return this.coor_long;
    }

    /**
     * Método que obtiene las coordenadas de un lugar
     * 
     * @return coordenadas del lugar
     */
    public String getCoordenadas() {
        String coordenadas = this.coor_lat + "," + " " + coor_long;
        return coordenadas;
    }

    /**
     * Método que obtiene la temperatura del lugar
     * 
     * @return temperatura
     */
    public String getTemperatura() {
        return this.temp;
    }

    /**
     * Método que obtiene el clima del lugar
     * 
     * @return clima
     */
    public String getClima() {
        return this.clima;
    }

    /**
     * Método que nos dice si el lugar es nacional o no.
     */
    public boolean esNacional() {

        if (this.pais.compareTo(PAIS_LOCAL) == 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean compareTo(Lugar o) {

        if (o.nombre.compareTo(this.nombre) == 0 && o.coor_lat.compareTo(this.coor_lat) == 0
                && o.coor_long.compareTo(this.coor_long) == 0 && o.pais.compareTo(this.pais) == 0) {
            return true;
        }
        return false;

    }

    @Override
    public String toString() {
    
        return this.pais + "\t" + rellenito(this.nombre,25) + "\t" + rellenito(this.clima,20) + "\t" + this.temp;
    }

    private String rellenito(String campo, int n) {
        String aux = campo;

        for (int i = 0; i < n - campo.length(); i++) {
            aux += " ";
        }
        return aux;

    }

}
