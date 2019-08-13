package model;

/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

public enum Tempo {
    BOM ("BOM"),
    NUBLADO ("NUBLADO"),
    CHUVOSO ("CHUVOSO");
    
    private final String tempo;

    private Tempo(String tempo) {
        this.tempo = tempo;
    }
    
    @Override
    public String toString() {
        return tempo;
    }
}
