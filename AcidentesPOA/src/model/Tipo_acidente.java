package model;

/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

public enum Tipo_acidente {
    ABALROAMENTO ("ABALROAMENTO"),
    ATROPELAMENTO ("ATROPELAMENTO"),
    CAPOTAGEM ("CAPOTAGEM"),
    CHOQUE ("CHOQUE"),
    COLISAO ("COLISÃO"),
    EVENTUAL ("EVENTUAL"),
    INCENDIO ("INCÊNDIO"),
    NAOCADASTRADO ("NÃO CADASTRADO"),
    QUEDA ("QUEDA"),
    TOMBAMENTO ("TOMBAMENTO");
    
    private final String tipo_acidente;

    private Tipo_acidente(String tipo_acidente) {
        this.tipo_acidente = tipo_acidente;
    }
    @Override
    public String toString() {
        return tipo_acidente;
    }
}
