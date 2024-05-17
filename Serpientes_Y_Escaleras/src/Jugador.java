import java.awt.Color;

public class Jugador {
    private String nombre;
    private int posicion;
    private Color color;

    public Jugador(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
        this.posicion = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Color getColor() {
        return color;
    }
}

