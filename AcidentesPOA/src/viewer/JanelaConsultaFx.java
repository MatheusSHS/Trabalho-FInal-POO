package viewer;

/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

import control.MyWaypoint;
import control.GerenciadorMapa;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Gerenciador;

public class JanelaConsultaFx extends Application {

    Gerenciador ger = Gerenciador.getInstance();

    private static Stage stage;
    final SwingNode mapkit = new SwingNode();
    private GerenciadorMapa gerenciador;
    private EventosMouse mouse;
    private GeoPosition poa;
    private BorderPane pane;
    private GridPane leftPane;
    private Button btnFechar;
    private Scene scene;
    private List<GeoPosition> geos;
    private GeoPosition loc;
    private Alert alert;

    public JanelaConsultaFx(List<GeoPosition> geos) {
        this.geos = geos;
        System.out.println(geos);
    }

    @Override
    public void start(Stage stage) throws Exception {
        alert = new Alert(Alert.AlertType.INFORMATION);
        
        setup();
        stage.setScene(scene);
        stage.setTitle("Mapas com JavaFX");
        stage.show();
        setStage(stage);
    }

    private void setup() {
        desenharMapa();

        if(geos.isEmpty()){
            alert.setContentText("Nenhum registro encontrado!");
            alert.showAndWait();
        }
        
        List<MyWaypoint> lstPoints = new ArrayList<>();

        gerenciador.clear();
        geos.forEach((geo) -> {
            lstPoints.add(new MyWaypoint(Color.RED, "", geo, 5));
        });
        gerenciador.setPontos(lstPoints);
        gerenciador.getMapKit().repaint();

        if (!geos.isEmpty()) {
            alert.setContentText("" + geos.size() + " acidentes registrados.");
            alert.showAndWait();
        }
    }

    private void desenharMapa() {
        ger.readFile("acidentes_trab.csv");
        poa = new GeoPosition(-30.025, -51.18);

        gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);

        mouse = new EventosMouse();

        gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
        gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);
        gerenciador.getMapKit().setZoom(6);

        createSwingContent(mapkit);

        pane = new BorderPane();

        leftPane = new GridPane();
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setHgap(10);
        leftPane.setVgap(10);
        leftPane.setPadding(new Insets(10, 10, 10, 10));

        btnFechar = new Button("Fechar");
        leftPane.add(btnFechar, 0, 0);

        pane.setCenter(mapkit);
        pane.setTop(leftPane);

        scene = new Scene(pane, 1800, 830);

        btnFechar.setOnAction(e -> {
            fechar();
        });
    }

    public static Stage getStage() {
        return stage;
    }

    public void setStage(Stage nStage) {
        stage = nStage;
    }

    private void fechar() {
        JanelaConsultaFx.getStage().close();
    }

    public GeoPosition getGeo() {
        return loc;
    }

    private class EventosMouse extends MouseAdapter {

        private int lastButton = -1;

        @Override
        public void mousePressed(MouseEvent e) {
            JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
            loc = mapa.convertPointToGeoPosition(e.getPoint());
            System.out.println(loc.getLatitude() + ", " + loc.getLongitude());
            lastButton = e.getButton();
            if (lastButton == MouseEvent.BUTTON3) {
                gerenciador.setPosicao(loc);
                gerenciador.getMapKit().repaint();
            }
        }
    }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            swingNode.setContent(gerenciador.getMapKit());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
