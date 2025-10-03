package yugiApi;

import java.util.List;
import java.util.Random;

public class Duelo {
    private List<Carta> cartasMaquina;
    private boolean turnoJugador;
    private int puntajeJugador = 0;
    private int puntajeMaquina = 0;
    private int round = 1;

    // Inicia aleatoriamente el turno
    public void inicio(){
        turnoJugador = new Random().nextBoolean();
    }

    // Determina que cuál es el jugador ganador del duelo según la carta
    public String dueloCartas(Carta cartaJugador1, Carta cartaJugador2){
        if (cartaJugador1.getAtk() > cartaJugador2.getDef()){
            return "Jugador 1";
        } else if (cartaJugador2.getDef() > cartaJugador1.getAtk()) {
            return "Maquina";
        } else {
            return "Empate";
        }
    }

    //Se selecciona una carta cualquiera para la máquina, obtiene el ganador y aumenta los puntajes según corresponda
    public String jugarTurno(Carta cartaJugador){
        Carta cartaMaquina = cartasMaquina.get(new Random().nextInt(cartasMaquina.size()));
        String ganador = dueloCartas(cartaJugador, cartaMaquina);

        if (ganador.equals("Jugador 1")){
            puntajeJugador++;
        } else if (ganador.equals("Maquina")) {
            puntajeMaquina++;
        }

        round++;
        turnoJugador = !turnoJugador;

        return "Batalla entre: " + cartaJugador.getNombre() +" ATK: "+cartaJugador.getAtk() + " DEF: " +cartaJugador.getDef() +
                " y " + cartaMaquina.getNombre() + " ATK: " + cartaMaquina.getAtk() + " DEF: " + cartaMaquina.getDef()+"\n" +
                ganador + " ganó la ronda " + (round-1) + "\nEl puntaje es: Jugador1: " + puntajeJugador + "    Maquina: " + puntajeMaquina +"\n";
    }

    // Verifica si el sí alguno de los dos jugadores llego a dos puntos
    public boolean seTerminoElduelo(){
        return puntajeJugador == 2 || puntajeMaquina == 2;
    }

    //Si se terminó el duelo obtiene el ganador
    public String obtenerGanador(){
        if (puntajeJugador==2){
            return "Jugador 1";
        } else if (puntajeMaquina==2) {
            return "Maquina";

        } return "empate";
    }

     //Constructor
    public Duelo(List<Carta> cartasMaquina) {
        this.cartasMaquina = cartasMaquina;
    }
}


