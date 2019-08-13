package model;

/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.jxmapviewer.viewer.GeoPosition;


public class Gerenciador {

    private static int mes = 1;
    private static int dia = 1;
    private static int ano = 2010;

    private static int diaMaior = 0;
    private static int mesMaior = 0;
    private static int anoMaior = 0;

    ArrayList<Acidente> lista;
    ArrayList<List<String>> csvData = new ArrayList<>();

    Set<LocalDate> datas = new HashSet<>();

    private static Gerenciador cad = null;

    public static Gerenciador getInstance() {
        if (cad == null) {
            cad = new Gerenciador();
        }
        return cad;
    }

    private Gerenciador() {
        lista = new ArrayList<>();
    }

    public int size() {
        return lista.size();
    }

    public void readFile(String nomeArq) {
        LocalDate data;
        try {
            Path path1 = Paths.get(nomeArq);

            BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"));

            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");

                List<String> dataLine = new ArrayList<>(dados.length);

                int fx_hora = Integer.parseInt(dados[31]);

                String tipoConvertido = dados[5].replaceAll("NAO CADASTRADO", "CHOQUE");
                Tipo_acidente tipo = Enum.valueOf(Tipo_acidente.class, tipoConvertido);

                String diaConvertido = dados[8].replaceAll("-FEIRA", "");
                Dia_semana dia_sem = Enum.valueOf(Dia_semana.class, diaConvertido);

                try { // gera exceção se o formato da data estiver invalido
                    data = LocalDateTime.parse(dados[7], DateTimeFormatter.ofPattern("yyyyMMdd HH:mm")).toLocalDate();
                } catch (Exception e) {
                    continue;
                }

                Dia_noite dia_noite = Enum.valueOf(Dia_noite.class, dados[24]);

                // tratamento da linha do .csv com problema
                String tempoConvertido = dados[23].replaceAll("NAO CADAST", "BOM");
                Tempo tempo = Enum.valueOf(Tempo.class, tempoConvertido);

                double lat = Double.parseDouble(dados[35].replaceAll(",", "."));
                double lon = Double.parseDouble(dados[36].replaceAll(",", "."));

                Acidente acidente = new Acidente(dados[0], data, dados[1], dados[2], tipo, dia_sem, dia_noite, tempo, fx_hora, new GeoPosition(lat, lon));

                lista.add(acidente);
                dataLine.addAll(Arrays.asList(dados));
                csvData.add(dataLine);
            }
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }

    // Questao 01a ok
    public int horaDoDiaComMaisAcidentes() {
        int maior = 0;
        int hora = 0;
        int horaMaior = -1;

        while (hora < 24) {
            int cont = 0;
            for (Acidente a : lista) {
                if (a.getFx_hora() == hora) {
                    cont++;
                }
            }
            if (cont > maior) {
                maior = cont;
                horaMaior = hora;
            }
            hora++;
        }
        return horaMaior;
    }

    // Questao 01b ok
    public ArrayList<GeoPosition> colecaoAcidentesPorHora(int hora) {
        ArrayList<GeoPosition> acidentes = new ArrayList<>();
        lista.stream().filter((acidente) -> (acidente.getFx_hora() == hora)).forEachOrdered((acidente) -> {
            acidentes.add(acidente.getGeo());
        });
        return acidentes;
    }

    // Questao 01 - Complemento ok
    public Map<Integer, Integer> mapaOcorrenciasPorHora() {
        Map<Integer, Integer> mapa = new HashMap<>();
        int hora = 0;

        while (hora < 24) {
            int cont = 0;
            for (Acidente a : lista) {
                if (a.getFx_hora() == hora) {
                    cont++;
                }
            }
            mapa.put(hora, cont);
            hora++;
        }
        return mapa;
    }

    // Questao 02 ok
    public ArrayList<GeoPosition> colecaoAcidentesPorLogDiaSem(String log, Dia_semana dia) {
        String logradouro = log.toUpperCase();
        ArrayList<GeoPosition> acidentes = new ArrayList<>();
        lista.stream().filter((acidente) -> (((acidente.getLog1().contains(logradouro)) || (acidente.getLog2().contains(logradouro))) && acidente.getDia_sem() == dia)).forEach((acidente) -> {
            acidentes.add(acidente.getGeo());
        });
        return acidentes;
    }

    // Questao 03 ok
    public ArrayList<GeoPosition> colecaoAcidentesPorDataTipoAcidente(LocalDate dataInicial, LocalDate dataFinal, List<Tipo_acidente> tipos) {
        ArrayList<GeoPosition> acidentes = new ArrayList<>();
        for (LocalDate id = dataInicial; !id.isAfter(dataFinal); id = id.plusDays(1)) {
            for (Acidente acidente : lista) {
                if ((id.equals(acidente.getData())) && (tipos.contains(acidente.getTipo()))) {
                    acidentes.add(acidente.getGeo());
                }
            }
        }
        return acidentes;
    }

    // Questao 04 ok
    public ArrayList<GeoPosition> acidentesPorLocalizacao(GeoPosition geo, double distancia) {
        ArrayList<GeoPosition> acidentes = new ArrayList<>();
        double grau = 0.011112;
        
        for (Acidente acidente : lista) {
            double x = Math.sqrt(Math.pow(geo.getLatitude() - acidente.getGeo().getLatitude(), 2) + Math.pow(geo.getLongitude() - acidente.getGeo().getLongitude(), 2));
            if (x <= distancia * grau) {
                acidentes.add(acidente.getGeo());
            }
        }
        return acidentes;
    }
        // Questao 05 ok
    public LocalDate dataComMaisAcidentes() {

        Map<Integer, Long> mapAno = new TreeMap<>();
        Map<Integer, Long> mapMes = new TreeMap<>();
        Map<Integer, Long> mapDia = new TreeMap<>();

        Long maior = Long.MIN_VALUE;

        while (ano <= 2012) {
            long contAno = lista.stream().filter(a -> (a.getData().getYear() == ano)).count();
            mapAno.put(ano, contAno);
            ano++;
        }
        for (int a : mapAno.keySet()) {
            if (mapAno.get(a) > maior) {
                maior = mapAno.get(a);
                anoMaior = a;
            }
        }

        maior = Long.MIN_VALUE;

        while (mes <= 12) {
            long contMes = lista.stream().filter(a -> (a.getData().getYear() == anoMaior) && (a.getData().getMonthValue() == mes)).count();
            mapMes.put(mes, contMes);
            mes++;
        }

        for (int m : mapMes.keySet()) {
            if (mapMes.get(m) > maior) {
                maior = mapMes.get(m);
                mesMaior = m;
            }
        }

        maior = Long.MIN_VALUE;

        while (dia <= 31) {
            long contDia = lista.stream().filter(a -> (a.getData().getYear() == anoMaior) && (a.getData().getMonthValue() == mesMaior) && (a.getData().getDayOfMonth() == dia)).count();
            mapDia.put(dia, contDia);
            dia++;
        }

        for (int d : mapDia.keySet()) {
            if (mapDia.get(d) > maior) {
                maior = mapDia.get(d);
                diaMaior = d;
            }
        }

        /**
         * Com dupla repetição iria demorar + de 10 min.
         * lista.stream().forEach((acidente1) -> { int cont = 0; LocalDate data1
         * = acidente1.getData(); cont = lista.stream().map((acidente2) ->
         * acidente2.getData()).filter((data2) ->
         * (data2.equals(data1))).map((item) -> 1).reduce(cont, Integer::sum);
         * map.put(data1, cont); });
         *
         *
         * for (LocalDate d : map.keySet()) { if (map.get(d) > maior) { maior =
         * map.get(d); dataMaior = d; } }
         */
        return LocalDate.of(anoMaior, mesMaior, diaMaior);
    }

    // Questao 06 ok
    public Enum diaSemanaComMaisAcidentes() {
        Map<Enum, Long> map = new TreeMap<>();
        long maior = Long.MIN_VALUE;
        Enum maiorEnum = null;

        // contagem de cada dia da semana utilizando stream
        long contSegunda = lista.stream().filter(a -> a.getDia_sem() == Dia_semana.SEGUNDA).count();
        long contTerca = lista.stream().filter(a -> a.getDia_sem() == Dia_semana.TERCA).count();
        long contQuarta = lista.stream().filter(a -> a.getDia_sem() == Dia_semana.QUARTA).count();
        long contQuinta = lista.stream().filter(a -> a.getDia_sem() == Dia_semana.QUINTA).count();
        long contSexta = lista.stream().filter(a -> a.getDia_sem() == Dia_semana.SEXTA).count();
        long contSabado = lista.stream().filter(a -> a.getDia_sem() == Dia_semana.SABADO).count();
        long contDomingo = lista.stream().filter(a -> a.getDia_sem() == Dia_semana.DOMINGO).count();

        List<Long> contadores = Arrays.asList(contSegunda, contTerca, contQuarta, contQuinta, contSexta, contSabado, contDomingo);
        List<Enum> dias = Arrays.asList(Dia_semana.values());

        for (int i = 0; i < Dia_semana.values().length; i++) {
            map.put(dias.get(i), contadores.get(i));
        }

        for (Enum e : map.keySet()) {
            if (map.get(e) > maior) {
                maior = map.get(e);
                maiorEnum = e;
            }
        }
        return maiorEnum;
    }

    // Questao 07 ok
    public Enum maiorOcorrenciadiaNoite() {
        Map<Enum, Long> map = new TreeMap<>();
        long maior = Long.MIN_VALUE;
        Enum maiorEnum = null;

        // contagem de cada dia ou noite utilizando stream
        long contDia = lista.stream().filter(a -> a.getDia_noite() == Dia_noite.DIA).count();
        long contNoite = lista.stream().filter(a -> a.getDia_noite() == Dia_noite.NOITE).count();

        List<Long> contadores = Arrays.asList(contDia, contNoite);
        List<Enum> diaNoite = Arrays.asList(Dia_noite.values());

        for (int i = 0; i < Dia_noite.values().length; i++) {
            map.put(diaNoite.get(i), contadores.get(i));
        }

        for (Enum e : map.keySet()) {
            if (map.get(e) > maior) {
                maior = map.get(e);
                maiorEnum = e;
            }
        }
        return maiorEnum;
    }

    // Questao 08 ok
    public Enum maiorOcorrenciaTempo() {
        Map<Enum, Long> map = new TreeMap<>();
        long maior = Long.MIN_VALUE;
        Enum maiorEnum = null;

        // contagem de cada tempo utilizando stream
        long contBom = lista.stream().filter(a -> a.getTempo() == Tempo.BOM).count();
        long contNublado = lista.stream().filter(a -> a.getTempo() == Tempo.NUBLADO).count();
        long contChuvoso = lista.stream().filter(a -> a.getTempo() == Tempo.CHUVOSO).count();

        List<Long> contadores = Arrays.asList(contBom, contNublado, contChuvoso);
        List<Enum> tempo = Arrays.asList(Tempo.values());

        for (int i = 0; i < Tempo.values().length; i++) {
            map.put(tempo.get(i), contadores.get(i));
        }

        for (Enum e : map.keySet()) {
            if (map.get(e) > maior) {
                maior = map.get(e);
                maiorEnum = e;
            }
        }
        return maiorEnum;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
