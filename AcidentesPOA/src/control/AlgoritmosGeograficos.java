package control;

/**
 * ProjetoFinal AcidentesPOA v.3.85
 * @autores: Alberto Pinalli, Edson Costa e Matheus Silva
 */

import org.jxmapviewer.viewer.GeoPosition;

public class AlgoritmosGeograficos {

    public static final double R = 6372.8; // In kilometers

    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    public static double calcDistancia(GeoPosition p1, GeoPosition p2) {
        return haversine(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude());
    }
}
