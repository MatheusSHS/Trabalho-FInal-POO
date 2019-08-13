package model;

/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

public enum Dia_semana {
    SEGUNDA ("SEGUNDA"),
    TERCA ("TERCA"),
    QUARTA ("QUARTA"),
    QUINTA ("QUINTA"),
    SEXTA ("SEXTA"),
    SABADO ("SABADO"),
    DOMINGO ("DOMINGO");
    
    private final String dia_semana;

    private Dia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }
    
    @Override
    public String toString() {
        return dia_semana;
    }
}
