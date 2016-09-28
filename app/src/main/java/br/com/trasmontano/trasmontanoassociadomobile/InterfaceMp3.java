package br.com.trasmontano.trasmontanoassociadomobile;

/**
 * Created by rbarbosa on 25/07/2016.
 */
public interface InterfaceMp3 {
    // Inicia a musica
    void play(String mp3);
    // Faz pause da musica
    void pause();
    // Para a musica
    void stop();
    // true se esta tocando
    boolean isPlaying();
    // Caminho da musica
    String getMp3();
}
