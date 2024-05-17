import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tablero {
    private int filas;
    private int columnas;
    private int numCasillas;
    private Map<Integer, Integer> casillasEspeciales;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.numCasillas = filas * columnas;
        this.casillasEspeciales = new HashMap<>();
        inicializarCasillasEspeciales();
    }

    private void inicializarCasillasEspeciales() {
        List<Integer> posicionesEspeciales = generarPosicionesAleatorias(0, numCasillas - 1, numCasillas / 10); 
        Random random = new Random();
        for (int posicion : posicionesEspeciales) {
            int tipo = random.nextInt(2); 
            int destino;
            if (tipo == 0) {
                destino = posicion - random.nextInt(posicion);
            } else {
                destino = posicion + random.nextInt(numCasillas - posicion);
            }
            casillasEspeciales.put(posicion + 1, destino + 1);
        }
    }

    private List<Integer> generarPosicionesAleatorias(int minimo, int maximo, int cantidad) {
        List<Integer> posiciones = new ArrayList<>();
        Random random = new Random();
        while (posiciones.size() < cantidad) {
            int posicion = random.nextInt(maximo - minimo + 1) + minimo;
            if (!posiciones.contains(posicion)) {
                posiciones.add(posicion);
            }
        }
        return posiciones;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getNumCasillas() {
        return numCasillas;
    }

    public int generaNumeroAleatorio(int minimo, int maximo) {
        return (int) (Math.random() * (maximo - minimo + 1) + minimo);
    }

    public int aplicarCasillaEspecial(int posicion) {
        if (casillasEspeciales.containsKey(posicion + 1)) {
            int destino = casillasEspeciales.get(posicion + 1);
            mostrarMensajeEspecial(destino > posicion + 1);
            return destino - 1;
        }
        return posicion;
    }

    private void mostrarMensajeEspecial(boolean esEscalera) {
        String mensaje = esEscalera ? "El jugador ha encontrado una escalera!" : "El jugador ha encontrado una serpiente!";
        System.out.println(mensaje);
    }
}