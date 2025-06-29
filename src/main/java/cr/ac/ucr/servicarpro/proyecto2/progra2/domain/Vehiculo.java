package cr.ac.ucr.servicarpro.proyecto2.progra2.domain;

import java.util.Objects;

public class Vehiculo {
    private String numeroPlaca;
    private String color;
    private String marca;
    private String estilo;
    private int anio;
    private String vin;
    private double cilindraje;
    private String duenio;

    public Vehiculo(String numeroPlaca, String color, String marca, String estilo, int anio, String vin, double cilindraje, String duenio) {
        this.numeroPlaca = numeroPlaca;
        this.color = color;
        this.marca = marca;
        this.estilo = estilo;
        this.anio = anio;
        this.vin = vin;
        this.cilindraje = cilindraje;
        this.duenio = duenio;
    }

    public Vehiculo() {
    }


    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public double getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(double cilindraje) {
        this.cilindraje = cilindraje;
    }

    public String getDuenio() {
        return duenio;
    }

    public void setDuenio(String duenio) {
        this.duenio = duenio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return getAnio() == vehiculo.getAnio() && Double.compare(getCilindraje(), vehiculo.getCilindraje()) == 0 && Objects.equals(getNumeroPlaca(), vehiculo.getNumeroPlaca()) && Objects.equals(getColor(), vehiculo.getColor()) && Objects.equals(getMarca(), vehiculo.getMarca()) && Objects.equals(getEstilo(), vehiculo.getEstilo()) && Objects.equals(getVin(), vehiculo.getVin()) && Objects.equals(getDuenio(), vehiculo.getDuenio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroPlaca(), getColor(), getMarca(), getEstilo(), getAnio(), getVin(), getCilindraje(), getDuenio());
    }
}