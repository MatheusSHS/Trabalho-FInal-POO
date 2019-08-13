package control;

/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

import java.util.List;
import java.util.LinkedList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class FlightData{
    private static final FlightData fd = new FlightData();
    
    private final List<String> diasSemana;
    
    private List<String> loadDiasSemana(){
        List<String> lst = new LinkedList<>();
        lst.add("SEGUNDA");
        lst.add("TERCA");
        lst.add("QUARTA");
        lst.add("QUINTA");
        lst.add("SEXTA");
        lst.add("SABADO");
        lst.add("DOMINGO");
        return lst;
    }
    
    private FlightData(){
        diasSemana = loadDiasSemana();
    }
    
    public static FlightData getInstance(){
        return(fd);
    }
    
    public ObservableList getDiasSemana(){
        return FXCollections.observableList(diasSemana);
    }
}
