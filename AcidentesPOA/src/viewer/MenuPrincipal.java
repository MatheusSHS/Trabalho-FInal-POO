package viewer;

/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Gerenciador;

public class MenuPrincipal extends Application {

    Gerenciador ger = Gerenciador.getInstance();

    private static Stage stage;
    private GridPane grid;
    private Text titulo;
    private Button btnConsulta1;
    private Button btnConsulta2;
    private Button btnConsulta3;
    private Button btnConsulta4;
    private Button btnConsultasRestantes;
    private StackPane root;
    private Scene scene;
    private Alert alert; 

    @Override
    public void start(Stage stage) throws Exception {
        setup();
        stage.setTitle("Menu Principal");
        stage.setScene(scene);
        stage.show();
        setStage(stage);
    }

    private void setup() {
        ger.readFile("acidentes_trab.csv");
        
        alert = new Alert(Alert.AlertType.INFORMATION);
        
        grid = new GridPane();
        grid.setAlignment(Pos.BASELINE_LEFT);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        titulo = new Text("Menu Principal");
        titulo.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(titulo, 1, 2);

        btnConsulta1 = new Button("Consulta 1");
        btnConsulta2 = new Button("Consulta 2");
        btnConsulta3 = new Button("Consulta 3");
        btnConsulta4 = new Button("Consulta 4");
        btnConsultasRestantes = new Button("Consultas\nRestantes");

        grid.add(btnConsulta1, 0, 4);
        grid.add(new Label("Mapear acidentes pela hora com maior incidência"), 1, 4);
        grid.add(btnConsulta2, 0, 5);
        grid.add(new Label("Mapear acidentes por rua e dia da semana"), 1, 5);
        grid.add(btnConsulta3, 0, 6);
        grid.add(new Label("Mapear acidentes por periodo e tipo"), 1, 6);
        grid.add(btnConsulta4, 0, 7);
        grid.add(new Label("Mapear acidentes pelo raio de uma localização"), 1, 7);
        grid.add(btnConsultasRestantes, 0, 8);
        grid.add(new Label("Verificar data, dia da semana e tempo com mais acidentes"), 1, 8);

        root = new StackPane();
        root.getChildren().add(grid);

        scene = new Scene(root, 500, 300);

        btnConsulta1.setOnAction(e -> {
            abrirConsulta1();
        });

        btnConsulta2.setOnAction(e -> {
            abrirConsulta2();
        });

        btnConsulta3.setOnAction(e -> {
            abrirConsulta3();
        });

        btnConsulta4.setOnAction(e -> {
            abrirConsulta4();
        });

        btnConsultasRestantes.setOnAction(e -> {
            abrirConsultasRestantes();
        });
    }

    private void abrirConsulta1() {
        alert.setContentText("\nA hora com mais acidentes registrados é " + ger.horaDoDiaComMaisAcidentes() + "hs");
        alert.showAndWait();
        
        JanelaConsultaFx frame = new JanelaConsultaFx(ger.colecaoAcidentesPorHora(ger.horaDoDiaComMaisAcidentes()));
        try {
            frame.start(new Stage());
        } catch (Exception ex) {
            System.err.format("erro ao abrir mapa");
        }
    }

    private void abrirConsulta2() {
        Consulta2 frame = new Consulta2();
        try {
            frame.start(new Stage());
        } catch (Exception ex) {
            System.err.format("erro ao abrir tela de pesquisa");
        }
    }

    private void abrirConsulta3() {
        Consulta3 frame = new Consulta3();
        try {
            frame.start(new Stage());
        } catch (Exception ex) {
            System.err.format("erro ao abrir tela de pesquisa");
        }
    }

    private void abrirConsulta4() {
        MapaConsulta4 frame = new MapaConsulta4();
        try {
            frame.start(new Stage());
        } catch (Exception ex) {
            System.err.format("erro ao abrir mapa");
        }
    }

    private void abrirConsultasRestantes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = ger.dataComMaisAcidentes().format(formatter);
        
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("\nData com mais acidentes registrados: " + data
                + "\n\nDia da semana com mais acidentes registrados: " + ger.diaSemanaComMaisAcidentes()
                + "\n\nTurno com mais acidentes registrados: " + ger.maiorOcorrenciadiaNoite()
                + "\n\nTempo com mais acidentes registrados: " + ger.maiorOcorrenciaTempo());
        alert.showAndWait();
    }

    public static Stage getStage() {
        return stage;
    }

    public void setStage(Stage nStage) {
        stage = nStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
