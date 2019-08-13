package viewer;

/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Gerenciador;
import model.Tipo_acidente;

public class Consulta3 extends Application {

    Gerenciador ger = Gerenciador.getInstance();

    private final List<Tipo_acidente> tipos = new ArrayList<>();
    private static Stage stage;
    private GridPane grid;
    private Text tfTitulo;
    private GridPane mainGrid;
    private DatePicker dpIda, dpVolta;
    private CheckBox cBoxAbalroamento;
    private CheckBox cBoxAtropelamento;
    private CheckBox cBoxCapotagem;
    private CheckBox cBoxChoque;
    private CheckBox cBoxColisao;
    private CheckBox cBoxEventual;
    private CheckBox cBoxIncendio;
    private CheckBox cBoxNaoCadastrado;
    private CheckBox cBoxQueda;
    private CheckBox cBoxTombamento;
    private Button btnPesquisar;
    private Button btnCancelar;
    private HBox hbBtn;
    private StackPane root;
    private Scene scene;
    private Alert alert;

    @Override
    public void start(Stage stage) throws Exception {
        setup();
        stage.setTitle("Consulta3");
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

        grid.add(new Label("Data inicial:"), 0, 2);
        dpIda = new DatePicker();
        grid.add(dpIda, 1, 2);
        grid.add(new Label("Data final:"), 0, 3);
        dpVolta = new DatePicker();
        grid.add(dpVolta, 1, 3);

        btnPesquisar = new Button("Pesquisar");
        btnCancelar = new Button("Cancelar");

        hbBtn = new HBox(20);
        hbBtn.setAlignment(Pos.BASELINE_RIGHT);
        hbBtn.getChildren().add(btnPesquisar);
        hbBtn.getChildren().add(btnCancelar);
        grid.add(hbBtn, 0, 5);

        grid.add(mainGrid, 0, 2);

        cBoxAbalroamento = new CheckBox();
        cBoxAbalroamento.setText("Abalroamento");
        cBoxAbalroamento.setOnAction((event) -> {
            boolean selected = cBoxAbalroamento.isSelected();
        });
        grid.add(cBoxAbalroamento, 2, 2);

        cBoxAtropelamento = new CheckBox();
        cBoxAtropelamento.setText("Atropelamento");
        cBoxAtropelamento.setOnAction((event) -> {
            boolean selected = cBoxAtropelamento.isSelected();
        });
        grid.add(cBoxAtropelamento, 2, 3);

        cBoxCapotagem = new CheckBox();
        cBoxCapotagem.setText("Capotagem");
        cBoxCapotagem.setOnAction((event) -> {
            boolean selected = cBoxCapotagem.isSelected();
        });
        grid.add(cBoxCapotagem, 2, 4);

        cBoxChoque = new CheckBox();
        cBoxChoque.setText("Choque");
        cBoxChoque.setOnAction((event) -> {
            boolean selected = cBoxChoque.isSelected();
        });
        grid.add(cBoxChoque, 2, 5);

        cBoxColisao = new CheckBox();
        cBoxColisao.setText("Colisão");
        cBoxColisao.setOnAction((event) -> {
            boolean selected = cBoxColisao.isSelected();
        });
        grid.add(cBoxColisao, 2, 6);

        cBoxEventual = new CheckBox();
        cBoxEventual.setText("Eventual");
        cBoxEventual.setOnAction((event) -> {
            boolean selected = cBoxEventual.isSelected();
        });
        grid.add(cBoxEventual, 3, 2);

        cBoxIncendio = new CheckBox();
        cBoxIncendio.setText("Incendio");
        cBoxIncendio.setOnAction((event) -> {
            boolean selected = cBoxIncendio.isSelected();
        });
        grid.add(cBoxIncendio, 3, 3);

        cBoxNaoCadastrado = new CheckBox();
        cBoxNaoCadastrado.setText("Não Cadastrado");
        cBoxNaoCadastrado.setOnAction((event) -> {
            boolean selected = cBoxNaoCadastrado.isSelected();
        });
        grid.add(cBoxNaoCadastrado, 3, 4);

        cBoxQueda = new CheckBox();
        cBoxQueda.setText("Queda");
        cBoxQueda.setOnAction((event) -> {
            boolean selected = cBoxQueda.isSelected();
        });
        grid.add(cBoxQueda, 3, 5);

        cBoxTombamento = new CheckBox();
        cBoxTombamento.setText("Tombamento");
        cBoxTombamento.setOnAction((event) -> {
            boolean selected = cBoxTombamento.isSelected();
        });
        grid.add(cBoxTombamento, 3, 6);

        root = new StackPane();
        root.getChildren().add(grid);

        scene = new Scene(root, 650, 300);

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

    public LocalDate getDataInicial() {
        return dpIda.getValue();
    }

    public LocalDate getDataFinal() {
        return dpVolta.getValue();
    }

    public List<Tipo_acidente> getTipos() {
        if (cBoxAbalroamento.isSelected()) {
            tipos.add(Tipo_acidente.ABALROAMENTO);
        }
        if (cBoxAtropelamento.isSelected()) {
            tipos.add(Tipo_acidente.ATROPELAMENTO);
        }
        if (cBoxCapotagem.isSelected()) {
            tipos.add(Tipo_acidente.CAPOTAGEM);
        }
        if (cBoxChoque.isSelected()) {
            tipos.add(Tipo_acidente.CHOQUE);
        }
        if (cBoxColisao.isSelected()) {
            tipos.add(Tipo_acidente.COLISAO);
        }
        if (cBoxEventual.isSelected()) {
            tipos.add(Tipo_acidente.EVENTUAL);
        }
        if (cBoxIncendio.isSelected()) {
            tipos.add(Tipo_acidente.INCENDIO);
        }
        if (cBoxNaoCadastrado.isSelected()) {
            tipos.add(Tipo_acidente.NAOCADASTRADO);
        }
        if (cBoxQueda.isSelected()) {
            tipos.add(Tipo_acidente.QUEDA);
        }
        if (cBoxTombamento.isSelected()) {
            tipos.add(Tipo_acidente.TOMBAMENTO);
        }
        return tipos;
    }

    private boolean pesquisar() {
        alert = new Alert(Alert.AlertType.WARNING);
        if (dpIda.getValue().isBefore(dpVolta.getValue()) || (dpIda.getValue().equals(dpVolta.getValue()))) {
            if (!getTipos().isEmpty()) {
                JanelaConsultaFx frame = new JanelaConsultaFx(ger.colecaoAcidentesPorDataTipoAcidente(dpIda.getValue(), dpVolta.getValue(), getTipos()));
                try {
                    frame.start(new Stage());
                    return true;
                } catch (Exception ex) {
                    System.err.format("erro ao pesquisar");
                }
            } else {
                alert.setContentText("\nSelecione pelo menos 1 tipos de acidente");
                alert.showAndWait();
            }
        } else {
            alert.setContentText("\nParametros de data inválidos");
            alert.showAndWait();
        }
        return false;
    }

    private void fechar() {
        Consulta3.getStage().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
