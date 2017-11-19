/**
 *Esta clase se encarga de obteer las distacias entre dos puntos usando la formula de Haversine
 */
public class HaversineF {
    /**
     * Constructor de la clase
     */
    public HaversineF(){

 }

    /**
     * Con esta funcion se obitne la distancia entre dos puntos segun latitud y longitud de cada uno con la formula de Haversine
     * @param lat1 latitud del primer nodo (ciudad)
     * @param lon1 longitud del primer nodo (ciudad)
     * @param lat2 latitud del segundo nodo (ciudad)
     * @param lon2 longitud del segundo nodo (ciudad)
     * @return retorna la distancia entre los dos puntos como double
     */
 static double distancia(double lat1, double lon1, double lat2, double lon2){
     final int tierraRadio = 6371; //radio de la tierra
     double distLatitudes = aRad(lat2-lat1);
     double distLongitudes = aRad(lon2-lon1);
     double a = Math.sin(distLatitudes / 2) * Math.sin(distLatitudes / 2) +
             Math.cos(aRad(lat1)) * Math.cos(aRad(lat2)) *
                     Math.sin(distLongitudes / 2) * Math.sin(distLongitudes / 2);
     Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
     Double distancia = tierraRadio * c;
       return distancia;

 }

    /**
     * Transforma a radianes la diferencia entre las latitudes y las longitudes
     * @param v la diferencia entre latitudes y longitudes
     * @return regresa la diferencia en radianes como double
     */
 private static double aRad(double v){
     return v * Math.PI / 180;
 }

}
