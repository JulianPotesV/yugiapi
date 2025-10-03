package yugiApi;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class YugiApi {
    private JPanel mainPanel;
    private JLabel carta1Jugador1;
    private JLabel carta2Jugador1;
    private JLabel carta3Jugador1;
    private JLabel nombreCarta1Jugador1;
    private JLabel atkCarta1jugador1;
    private JLabel defCarta1jugador1;
    private JLabel nombreCarta2Jugador1;
    private JLabel nombreCarta3Jugador1;
    private JLabel atkCarta2jugador1;
    private JLabel atkCarta3jugador1;
    private JLabel defCarta2jugador1;
    private JLabel defCarta3jugador1;
    private JLabel carta1Jugador2;
    private JLabel carta2Jugador2;
    private JLabel carta3Jugador2;
    private JLabel atkCarta1jugador2;
    private JLabel defCarta1jugador2;
    private JLabel atkCarta2jugador2;
    private JLabel defCarta2jugador2;
    private JLabel atkCarta3jugador2;
    private JLabel defCarta3jugador2;
    private JLabel nombreCarta1Jugador2;
    private JLabel nombreCarta2Jugador2;
    private JLabel nombreCarta3Jugador2;
    private JTextArea textArea1;
    private JButton jugarTurno;
    private JButton iniciarDuelo;
    private JRadioButton seleccionarCarta1;
    private JRadioButton seleccionarCarta2;
    private JRadioButton seleccionarCarta3;

    private Carta objetoCarta1;
    private Carta objetoCarta2;
    private Carta objetoCarta3;

    private java.util.List<Carta> cartasMaquina = new java.util.ArrayList<>();

   private Duelo duelo;

    public YugiApi(){

        ButtonGroup grupoCartas = new ButtonGroup();
        grupoCartas.add(seleccionarCarta1);
        grupoCartas.add(seleccionarCarta2);
        grupoCartas.add(seleccionarCarta3);

        // Action listeners
        jugarTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (duelo == null){
                    JOptionPane.showMessageDialog(null, "Primero debes iniciar el duelo");
                    return;
                }

               Carta cartaJugador = null;

               if (seleccionarCarta1.isSelected()) {cartaJugador = objetoCarta1;}
               else if (seleccionarCarta2.isSelected()) {cartaJugador = objetoCarta2;}
               else if (seleccionarCarta3.isSelected()) {cartaJugador = objetoCarta3;}

               if(cartaJugador == null){
                   JOptionPane.showMessageDialog(null, "Debes seleccionar una carta primero");
                   return;
               }
               String resultado = duelo.jugarTurno(cartaJugador);
               textArea1.append(resultado + "\n");

               if(duelo.seTerminoElduelo()){
                   textArea1.append("El ganador del duelo es: " + duelo.obtenerGanador()+ "\n");
                   jugarTurno.setEnabled(false);
               }
            }
        });
        iniciarDuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 1; i <= 3 ; i++) {

                    rellenar(conectar(),1, i);
                }

                for (int i = 1; i <= 3 ; i++) {
                    rellenar(conectar(),2, i);
                }

                duelo = new Duelo(cartasMaquina);
                duelo.inicio();
                textArea1.append("Cartas repartidas. ¡Selecciona una carta y presiona 'Jugar Turno' para jugar!\n\n");
            }
        });
    }
    // Funciones
    public Carta conectar(){
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://db.ygoprodeck.com/api/v7/cardinfo.php?type=Normal%20Monster")).build();
            HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());

            if(response.statusCode()==200){
                JSONObject json = new JSONObject(response.body());

                JSONArray data = json.getJSONArray("data");
                int randomIndex = new Random().nextInt(data.length());
                JSONObject cartaJson = data.getJSONObject(randomIndex);

                String nombre = cartaJson.getString("name");
                int atk = cartaJson.getInt("atk");
                int def = cartaJson.getInt("def");

                JSONArray imagenCarta = cartaJson.getJSONArray("card_images");
                String imagen = imagenCarta.getJSONObject(0).getString("image_url");

                return new Carta(nombre,atk,def,imagen);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void rellenar(Carta carta, int jugador, int numeroCarta){
        try {
            java.net.URL urlImagen = new java.net.URL(carta.getImagen());
            ImageIcon icon = new ImageIcon(urlImagen);
            Image image = icon.getImage().getScaledInstance(220, 300, Image.SCALE_SMOOTH);

            if(jugador == 1){
                if (numeroCarta == 1){
                    objetoCarta1 = carta;
                    carta1Jugador1.setIcon(new ImageIcon(image));
                    carta1Jugador1.setText("");

                    nombreCarta1Jugador1.setText(carta.getNombre());
                    atkCarta1jugador1.setText(String.valueOf(carta.getAtk()));
                    defCarta1jugador1.setText(String.valueOf(carta.getDef()));

                } else if (numeroCarta == 2) {
                    objetoCarta2 = carta;
                    carta2Jugador1.setIcon(new ImageIcon(image));
                    carta2Jugador1.setText("");

                    nombreCarta2Jugador1.setText(carta.getNombre());
                    atkCarta2jugador1.setText(String.valueOf(carta.getAtk()));
                    defCarta2jugador1.setText(String.valueOf(carta.getDef()));

                } else {
                    objetoCarta3 = carta;
                    carta3Jugador1.setIcon(new ImageIcon(image));
                    carta3Jugador1.setText("");

                    nombreCarta3Jugador1.setText(carta.getNombre());
                    atkCarta3jugador1.setText(String.valueOf(carta.getAtk()));
                    defCarta3jugador1.setText(String.valueOf(carta.getDef()));
                }
            } else if (jugador == 2) {
                cartasMaquina.add(carta);
                if (numeroCarta == 1) {
                    carta1Jugador2.setIcon(new ImageIcon(image));
                    carta1Jugador2.setText("");

                    nombreCarta1Jugador2.setText(carta.getNombre());
                    atkCarta1jugador2.setText(String.valueOf(carta.getAtk()));
                    defCarta1jugador2.setText(String.valueOf(carta.getDef()));

                } else if (numeroCarta == 2) {
                    carta2Jugador2.setIcon(new ImageIcon(image));
                    carta2Jugador2.setText("");

                    nombreCarta2Jugador2.setText(carta.getNombre());
                    atkCarta2jugador2.setText(String.valueOf(carta.getAtk()));
                    defCarta2jugador2.setText(String.valueOf(carta.getDef()));

                } else if (numeroCarta == 3) {
                    carta3Jugador2.setIcon(new ImageIcon(image));
                    carta3Jugador2.setText("");

                    nombreCarta3Jugador2.setText(carta.getNombre());
                    atkCarta3jugador2.setText(String.valueOf(carta.getAtk()));
                    defCarta3jugador2.setText(String.valueOf(carta.getDef()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void main(String[] args) {
        JFrame frame = new JFrame("PokeStadium");
        frame.setContentPane(new YugiApi().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1600,900);
        frame.setLocationRelativeTo(null);

    }
}
