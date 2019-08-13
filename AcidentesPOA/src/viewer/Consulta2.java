package viewer;

/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

import control.FlightData;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Dia_semana;
import model.Gerenciador;

public class Consulta2 extends Application {

    Gerenciador ger = Gerenciador.getInstance();

    private static Stage stage;
    private GridPane grid;
    private Text tfTitulo;
    private GridPane mainGrid;
    private TextField cp_logradouro;
    private ComboBox cbDia_semana;
    private Button btnPesquisar;
    private Button btnCancelar;
    private HBox hbBtn;
    private StackPane root;
    private Scene scene;
    private Alert alert;

    @Override
    public void start(Stage stage) throws Exception {
        setup();
        stage.setTitle("Consulta2");
        stage.setScene(scene);
        stage.show();
        setStage(stage);
    }

    private void setup() {
        ger.readFile("acidentes_trab.csv");

        grid = new GridPane();
        grid.setAlignment(Pos.BASELINE_LEFT);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        tfTitulo = new Text("Selecione sua busca");
        tfTitulo.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(tfTitulo, 0, 0, 2, 1);

        mainGrid = new GridPane();
        mainGrid.setAlignment(Pos.BASELINE_LEFT);
        mainGrid.setHgap(4);
        mainGrid.setVgap(6);
        mainGrid.add(new Label("Logradouro:"), 0, 1);
        cp_logradouro = new TextField();
        cp_logradouro.setPrefWidth(210);

        cbDia_semana = new ComboBox(FlightData.getInstance().getDiasSemana());

        mainGrid.add(cp_logradouro, 1, 1);
        mainGrid.add(new Label("Dia da Semana:"), 0, 2);
        mainGrid.add(cbDia_semana, 1, 2);

        btnPesquisar = new Button("Pesquisar");
        btnCancelar = new Button("Cancelar");

        hbBtn = new HBox(20);
        hbBtn.setAlignment(Pos.BASELINE_CENTER);
        hbBtn.getChildren().add(btnPesquisar);
        hbBtn.getChildren().add(btnCancelar);

        grid.add(hbBtn, 1, 4);
        grid.add(mainGrid, 0, 2);

        root = new StackPane();
        root.getChildren().add(grid);

        scene = new Scene(root, 530, 200);

        btnPesquisar.setOnAction(e -> {
            if (pesquisar()) {
                fechar();
            }
        });

        btnPesquisar.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                if (pesquisar()) {
                    fechar();
                }
            }
        });

        btnCancelar.setOnAction(e -> {
            fechar();
        });

        btnCancelar.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                fechar();
            }
        });
    }

    public static Stage getStage() {
        return stage;
    }

    public void setStage(Stage nStage) {
        stage = nStage;
    }

    public Dia_semana pesquisaDiaSemana(int index) {
        switch (cbDia_semana.getSelectionModel().getSelectedIndex()) {
            case 0:
                return Dia_semana.SEGUNDA;
            case 1:
                return Dia_semana.TERCA;
            case 2:
                return Dia_semana.QUARTA;
            case 3:
                return Dia_semana.QUINTA;
            case 4:
                return Dia_semana.SEXTA;
            case 5:
                return Dia_semana.SABADO;
            case 6:
                return Dia_semana.DOMINGO;
            default:
                System.out.println("deu bret");
                return null;
        }
    }

    public boolean pesquisar() {
        alert = new Alert(Alert.AlertType.WARNING);
        if (!cp_logradouro.getText().isEmpty()) {
            if (!cbDia_semana.getSelectionModel().isEmpty()) {
                JanelaConsultaFx frame = new JanelaConsultaFx(ger.colecaoAcidentesPorLogDiaSem(cp_logradouro.getText(), pesquisaDiaSemana(cbDia_semana.getSelectionModel().getSelectedIndex())));

                try {
                    frame.start(new Stage());
                    return true;
                } catch (Exception ex) {
                    System.err.format("erro ao abrir o mapa");
                }
            } else {
                alert.setContentText("\nInforme o dia da semana");
                alert.showAndWait();
            }
        } else {
            alert.setContentText("\nDigite o logradouro");
            alert.showAndWait();
        }
        return false;
    }

    public void fechar() {
        Consulta2.getStage().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
