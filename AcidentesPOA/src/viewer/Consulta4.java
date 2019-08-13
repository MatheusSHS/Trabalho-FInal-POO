package viewer;

/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

import javafx.application.Application;
import static javafx.application.Application.launch;
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
import model.Gerenciador;
import org.jxmapviewer.viewer.GeoPosition;

public class Consulta4 extends Application {

    Gerenciador ger = Gerenciador.getInstance();

    private static Stage stage;
    private GridPane grid;
    private Text tfTitulo;
    private GridPane mainGrid;
    private TextField cp_distancia;
    private ComboBox cbDia_semana;
    private Button btnPesquisar;
    private Button btnCancelar;
    private HBox hbBtn;
    private StackPane root;
    private Scene scene;
    private GeoPosition geo;

    public Consulta4(GeoPosition geo) {
        this.geo = geo;
    }

    public Consulta4() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        setup();
        stage.setTitle("Consulta4");
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
        mainGrid.add(new Label("Distância em Km:"), 0, 1);
        cp_distancia = new TextField();
        cp_distancia.setPrefWidth(100);
        mainGrid.add(cp_distancia, 1, 1);
        grid.add(mainGrid, 0, 2);

        btnPesquisar = new Button("Pesquisar");
        grid.add(btnPesquisar, 2, 5);
        btnCancelar = new Button("Cancelar");
        grid.add(btnCancelar, 3, 5);

        root = new StackPane();
        root.getChildren().add(grid);

        scene = new Scene(root, 480, 200);

        btnPesquisar.setOnAction(e -> {
            pesquisar();
        });

        btnPesquisar.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                pesquisar();
            }
        });

        btnCancelar.setOnAction(e -> {
            fechar();
        });
    }

    public GeoPosition getGeo() {
        return geo;
    }

    public static Stage getStage() {
        return stage;
    }

    public void setStage(Stage nStage) {
        stage = nStage;
    }

    private void pesquisar() {
        if (!cp_distancia.getText().isEmpty()) {
            JanelaConsultaFx frame = new JanelaConsultaFx(ger.acidentesPorLocalizacao(getGeo(), Long.parseLong(cp_distancia.getText())));
            try {
                frame.start(new Stage());
            } catch (Exception ex) {
                System.out.println("erro ao abrir o mapa");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Informe um distancia!");
            alert.showAndWait();
        }
    }

    public void fechar() {
        Consulta4.getStage().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
