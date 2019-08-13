
/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import model.Dia_semana;
import model.Gerenciador;
import model.Tipo_acidente;
import org.jxmapviewer.viewer.GeoPosition;

public class AppTestes {

    public static void main(String[] args) {
        Gerenciador ger = Gerenciador.getInstance();

        ger.readFile("acidentes_trab.csv");
        
        
        System.out.println("Numero de registros válidos criados: " + ger.size() + "\n");

        /*
         * 1. Verificar a hora do dia quando ocorrem mais acidentes (dados
         * intervalos de horas cheias: 13:00 até 13:59, 14:00h até 14:59h, etc).
         * Mostre no mapa os locais dos acidentes que ocorrem neste horário;
         */
        System.out.println("1.\nOcorrencias de acidentes por hora:\n" + ger.mapaOcorrenciasPorHora());
        System.out.println("Hora do dia com mais acidentes: " + ger.horaDoDiaComMaisAcidentes() + "hs");
        System.out.println(ger.colecaoAcidentesPorHora(ger.horaDoDiaComMaisAcidentes()) + "\n");

        /*
         * 2. Informar um nome de rua/avenida e um dia da semana e visualizar
         * todos os acidentes que ocorreram na rua e no dia indicados;
         */
        String log = "IcArAi";
        Dia_semana dia = Dia_semana.TERCA;
        System.out.println("2.\nLog: " + log + "\nDia da Semana: " + dia + "\n" + ger.colecaoAcidentesPorLogDiaSem(log, dia) + "\n");

        /*
         * 3. Identificar para um determinado período (dia inicial e dia final,
         * informado/selecionado pelo usuário), quais avenidas ou ruas que
         * exibiram acidentes de um ou mais tipos, escolhidos pelo usuário (ex:
         * colisão, atropelamento, queda...). Mostrar no mapa os locais;
         */
        List<Tipo_acidente> tipos = Arrays.asList(Tipo_acidente.ABALROAMENTO, Tipo_acidente.COLISAO, Tipo_acidente.TOMBAMENTO);
        LocalDate dataInicial = LocalDate.of(2010, Month.MARCH, 5);
        LocalDate dataFinal = LocalDate.of(2010, Month.MARCH, 20);
        System.out.println("3.\nData Inicial: " + dataInicial + "\nData Final: " + dataFinal + "\nTipos de Acidente: " + tipos + "\n" + ger.colecaoAcidentesPorDataTipoAcidente(dataInicial, dataFinal, tipos) + "\n");

        /*
         * 4. A partir de uma determinada localização mostrar todos os acidentes
         * que ocorreram a até uma determinada distância deste local (valor
         * informado pelo usuário);
         */
        GeoPosition geoTeste = new GeoPosition(-30.054831224852435, -51.16504669189453);
        int distancia = 2;
        System.out.println("4.\nGeoPosition: " + geoTeste + "\nDistancia: " + distancia + " km\n" + ger.acidentesPorLocalizacao(geoTeste, 2) + "\n");

        /*
         * 5. Identificar a data específica em que houve mais acidentes;
         */
        System.out.println("5. Data com mais acidentes: " + ger.dataComMaisAcidentes());

        /*
         * 6. Verificar o dia da semana em que houve mais acidentes;
         */
        System.out.println("6. Dia da semana com mais acidentes: " + ger.diaSemanaComMaisAcidentes());

        /*
         * 7. Verificar e informar se ocorrem mais acidentes de dia ou à noite;
         */
        System.out.println("7. Maior ocorrencia entre Dia ou Noite: " + ger.maiorOcorrenciadiaNoite());

        /*
         * 8. Verificar se ocorrem mais acidentes com tempo chuvoso, nublado ou bom. 
         */
        System.out.println("8. Maior ocorrencia entre tempo BOM, NUBLADO ou CHUVOSO: " + ger.maiorOcorrenciaTempo());
    }
}
