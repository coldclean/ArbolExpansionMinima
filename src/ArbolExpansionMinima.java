/**
 * Clase principal para iniciar el programa
 */
public class ArbolExpansionMinima {
    public static void main(String[] args) {
        DatosCiudades dciudades = new DatosCiudades("src/Cities.xml", "http://overpass-api.de/api/interpreter/?");
        DatosCiudades.Nodos arregloNodos[] = dciudades.obtenerCiudades();
        Kruskal.Arbol nodosCiudades[] = Kruskal.KruskalMST(arregloNodos);
        new Mapa(nodosCiudades, arregloNodos).setVisible(true);

    }
}
