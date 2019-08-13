package model;

/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

public enum Dia_noite {
    DIA ("DIA"),
    NOITE ("NOITE");
    
    private final String dia_noite;

    private Dia_noite(String dia_noite) {
        this.dia_noite = dia_noite;
    }
    
    @Override
    public String toString() {
        return dia_noite;
    }
}
