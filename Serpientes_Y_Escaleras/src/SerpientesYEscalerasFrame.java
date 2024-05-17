import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SerpientesYEscalerasFrame extends JFrame {

    private Tablero tablero;
    private ArrayList<Jugador> jugadores;
    private int turno = 0;

    private JPanel panelTablero;
    private JButton botonLanzarDado;
    private JLabel[] labelsJugadores;
    private JLabel[] labelsColoresJugadores;
    private JLabel labelTurno;

    public SerpientesYEscalerasFrame(Tablero tablero, ArrayList<Jugador> jugadores) {
        this.tablero = tablero;
        this.jugadores = jugadores;

        setTitle("Escaleras y Serpientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2));
        panelPrincipal.setBackground(Color.WHITE);

        panelTablero = new JPanel(new GridLayout(tablero.getFilas(), tablero.getColumnas()));
        panelTablero.setBackground(Color.WHITE);
        for (int i = 0; i < tablero.getNumCasillas(); i++) {
            JLabel labelCasilla = new JLabel("" + (i + 1), SwingConstants.CENTER);
            labelCasilla.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            labelCasilla.setOpaque(true);
            labelCasilla.setBackground(Color.WHITE);
            panelTablero.add(labelCasilla);
        }
        panelPrincipal.add(panelTablero);

        JPanel panelControles = new JPanel(new BorderLayout());
        panelControles.setBackground(Color.WHITE);

        JPanel panelBotones = new JPanel(new GridLayout(1, 1));
        panelBotones.setBackground(Color.WHITE);

        botonLanzarDado = new JButton("Lanzar Dado");
        botonLanzarDado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugarTurno();
            }
        });
        panelBotones.add(botonLanzarDado);

        panelControles.add(panelBotones, BorderLayout.NORTH);

        JPanel panelJugadores = new JPanel(new GridLayout(jugadores.size() * 2, 1));
        panelJugadores.setBackground(Color.WHITE);
        labelsJugadores = new JLabel[jugadores.size()];
        labelsColoresJugadores = new JLabel[jugadores.size()];
        for (int i = 0; i < jugadores.size(); i++) {
            labelsJugadores[i] = new JLabel(jugadores.get(i).getNombre() + ": Posicion " + (jugadores.get(i).getPosicion() + 1), SwingConstants.CENTER);
            labelsJugadores[i].setFont(new Font("Arial", Font.BOLD, 16));
            panelJugadores.add(labelsJugadores[i]);

            labelsColoresJugadores[i] = new JLabel();
            labelsColoresJugadores[i].setPreferredSize(new Dimension(20, 20));
            labelsColoresJugadores[i].setBackground(jugadores.get(i).getColor());
            labelsColoresJugadores[i].setOpaque(true);
            panelJugadores.add(labelsColoresJugadores[i]);
        }
        panelControles.add(panelJugadores, BorderLayout.CENTER);

        labelTurno = new JLabel("Turno del Jugador " + jugadores.get(turno).getNombre(), SwingConstants.CENTER);
        panelControles.add(labelTurno, BorderLayout.SOUTH);

        panelPrincipal.add(panelControles);

        add(panelPrincipal, BorderLayout.CENTER);

        pack();
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    private void jugarTurno() {
        int dado = tablero.generaNumeroAleatorio(1, 6);
        JOptionPane.showMessageDialog(this, "El jugador " + jugadores.get(turno).getNombre() + " ha sacado un " + dado);

        int nuevaPosicion = jugadores.get(turno).getPosicion() + dado;
        if (nuevaPosicion >= tablero.getNumCasillas()) {
            nuevaPosicion = tablero.getNumCasillas() - 1;
        }
        nuevaPosicion = tablero.aplicarCasillaEspecial(nuevaPosicion);

        jugadores.get(turno).setPosicion(nuevaPosicion);

        actualizarJugadores();
        actualizarTablero();
        actualizarTurno();

        if (nuevaPosicion == tablero.getNumCasillas() - 1) {
            JOptionPane.showMessageDialog(this, "El jugador " + jugadores.get(turno).getNombre() + " ha ganado");
            System.exit(0);
        }

        if (dado != 6) {
            turno = (turno + 1) % jugadores.size();
        }

        labelTurno.setText("Turno del Jugador " + jugadores.get(turno).getNombre());
    }

    private void actualizarTablero() {
        for (int i = 0; i < tablero.getNumCasillas(); i++) {
            ((JLabel) panelTablero.getComponent(i)).setBackground(Color.WHITE);
        }
        for (Jugador jugador : jugadores) {
            ((JLabel) panelTablero.getComponent(jugador.getPosicion())).setBackground(jugador.getColor());
        }
    }

    private void actualizarJugadores() {
        for (int i = 0; i < jugadores.size(); i++) {
            labelsJugadores[i].setText(jugadores.get(i).getNombre() + ": Posicion " + (jugadores.get(i).getPosicion() + 1));
        }
    }

    private void actualizarTurno() {
        labelTurno.setText("Turno del Jugador " + jugadores.get(turno).getNombre());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaIngresoDatos ventanaIngreso = new VentanaIngresoDatos();
            ventanaIngreso.setVisible(true);
        });
    }

    private static class VentanaIngresoDatos extends JFrame {
        private JLabel filasLabel, columnasLabel, jugadoresLabel;
        private JTextField filasField, columnasField, jugadoresField;
        private JButton empezarJuegoButton;

        public VentanaIngresoDatos() {
            setTitle("Escaleras y Serpientes");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridLayout(4, 2));
            setSize(400, 200);
            setResizable(false);
            setLocationRelativeTo(null);

            filasLabel = new JLabel("Filas (10, 13, 15):");
            add(filasLabel);
            filasField = new JTextField();
            add(filasField);

            columnasLabel = new JLabel("Columnas (10, 13, 15):");
            add(columnasLabel);
            columnasField = new JTextField();
            add(columnasField);

            jugadoresLabel = new JLabel("Cantidad de Jugadores (1-4):");
            add(jugadoresLabel);
            jugadoresField = new JTextField();
            add(jugadoresField);

            empezarJuegoButton = new JButton("Empezar Juego");
            empezarJuegoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int filas = Integer.parseInt(filasField.getText());
                    int columnas = Integer.parseInt(columnasField.getText());
                    int numJugadores = Integer.parseInt(jugadoresField.getText());

                    if ((filas == 10 || filas == 13 || filas == 15) &&
                        (columnas == 10 || columnas == 13 || columnas == 15) &&
                        (numJugadores >= 1 && numJugadores <= 4)) {
                        ArrayList<Jugador> jugadores = new ArrayList<>();
                        for (int i = 0; i < numJugadores; i++) {
                            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del jugador " + (i + 1) + ":");
                            jugadores.add(new Jugador(nombre, getColorJugador(i)));
                        }
                        Tablero tablero = new Tablero(filas, columnas);
                        SerpientesYEscalerasFrame juego = new SerpientesYEscalerasFrame(tablero, jugadores);
                        juego.setVisible(true);
                        dispose(); // Cerramos la ventana de ingreso de datos
                    } else {
                        JOptionPane.showMessageDialog(null, "Ingrese valores validos para filas, columnas y cantidad de jugadores.");
                    }
                }
            });
            add(empezarJuegoButton);
        }

        private static Color getColorJugador(int jugador) {
            switch (jugador) {
                case 0:
                    return Color.BLUE;
                case 1:
                    return Color.GREEN;
                case 2:
                    return Color.PINK;
                case 3:
                    return Color.YELLOW;
                default:
                    return Color.BLACK;
            }
        }
    }
}