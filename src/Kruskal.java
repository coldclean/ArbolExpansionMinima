import java.util.Arrays;
import java.util.Comparator;

/**
 * La clase Kruskal maneja todas las operaciones para generar el arbol de expansion segun Kruskal
 */

public class Kruskal {
    static final int MAX = 1005;  //maximo numero de vértices
    static int padre[] = new int[MAX];  //Este arreglo contiene el padre del i-esimo nodo

    /**
     * Método para inicializar los componentes
     *
     * @param n recorrido maximo del arreglo
     */
    static void MakeSet(int n) {
        for (int i = 1; i <= n; ++i) padre[i] = i;
    }

    /**
     * Metodo recursivo para encontrar la raiz del vértice actual X
     *
     * @param x indice del vertice actual
     * @return retorna el padre del elemento pasado como parametro
     */
    static int Find(int x) {
        return (x == padre[x]) ? x : (padre[x] = Find(padre[x]));
    }

    /**
     * Método para unir 2 componentes conexas
     *
     * @param x indice del primer componente conexo
     * @param y indice del segundo componente conexo
     */
    static void Union(int x, int y) {
        padre[Find(x)] = Find(y);
    }

    /**
     * Método que me determina si 2 vértices estan o no en la misma componente conexa
     *
     * @param x indice del primer indice a unir
     * @param y indice del segundo indice a unir
     * @return boleano indica si ambos estan en la misma componente
     */
    static boolean sameComponent(int x, int y) {
        if (Find(x) == Find(y)) return true;
        return false;
    }
    static int V, E;      //numero de vertices y aristas
    /**
     * La clase camino se usa para instanciar los caminos entre la ciudad de origen y de destino y el peso
     */
    static class Edge implements Comparator<Edge> {
        int origen;     //Vértice origen
        int destino;    //Vértice destino
        double peso;       //Peso entre el vértice origen y destino

        Edge() {
        }

        //Comparador por peso, me servira al momento de ordenar lo realizara en orden ascendente
        //Ordenar de forma descendente para obtener el arbol de expansion maxima

        /**
         * Sobre escritura del meotodo compare para comparar los pesos de las aristas
         *
         * @param e1 arista 1 del arreglo
         * @param e2 arista 2 del arreglo
         * @return entero para conocer cual es el mayor
         */
        @Override
        public int compare(Edge e1, Edge e2) {
            //return e2.peso - e1.peso; //Arbol de expansion maxima
            if (e1.peso < e2.peso) return -1;
            if (e1.peso > e2.peso) return 1;
            return 0;
        }
    }

    /**
     * clase para ser usado como tipo de dato para almacernar los datos de las ciudades de destino y origen y el valor de las distancias entre ellos
     */

    static class Arbol {
        String ciudadO, ciudadD;
        double distancia;
        double latOri, latDes, lonOri, lonDes;
    }

    static Edge arista[] = new Edge[MAX];      //Arreglo de aristas para el uso en kruskal
    static Edge MST[] = new Edge[MAX];     //Arreglo de aristas del MST encontrado

    /**
     * Esta funcion calcula el arbol de expansion
     *
     * @param InfoNodos recibe la informacion de los nodos en forma de arreglo
     * @return retorna el arbol de expansion en forma de arreglo de tipo Arbol
     */

    static Arbol[] KruskalMST(DatosCiudades.Nodos InfoNodos[]) {
        int origen, destino;
        double peso;
        double total = 0;          //Peso total del arbol
        int numAristas = 0;     //Numero de Aristas del arbol
        int k = 0;
        for (int i = 0; i < arista.length; i++) {
            arista[i] = new Edge();
        }

        MakeSet(V);           //Inicializa cada componente
        for (int i = 0; i < InfoNodos.length - 1; i++) {
            for (int j = i + 1; j < InfoNodos.length; j++) {
                arista[k].origen = i;
                arista[k].destino = j;
                arista[k].peso = HaversineF.distancia(Double.parseDouble(InfoNodos[i].lat), Double.parseDouble(InfoNodos[i].lon), Double.parseDouble(InfoNodos[j].lat), Double.parseDouble(InfoNodos[j].lon));
                k++;
            }
        }

        Arrays.sort(arista, 0, E, new Edge());    //Ordenamos las aristas por su comparador

        for (int i = 0; i < E; ++i) {     //Recorremos las aristas ya ordenadas por peso
            origen = arista[i].origen;    //Vértice origen de la arista actual
            destino = arista[i].destino;  //Vértice destino de la arista actual
            peso = arista[i].peso;        //Peso de la arista actual

            //Verificamos si estan o no en la misma componente conexa
            if (!sameComponent(origen, destino)) {
                total += peso;
                MST[numAristas++] = arista[i];
                Union(origen, destino);
            }
        }

        if (V - 1 != numAristas) { //verificamos que se cumpla la regla N-1 del algoritmo
            return null;
        }
        Arbol ar[] = new Arbol[numAristas];
        for (int i = 0; i < numAristas; ++i) {
            ar[i] = new Arbol();
            ar[i].ciudadO = InfoNodos[MST[i].origen].nombre;
            ar[i].ciudadD = InfoNodos[MST[i].destino].nombre;
            ar[i].latOri = Double.parseDouble(InfoNodos[MST[i].origen].lat);
            ar[i].lonOri = Double.parseDouble(InfoNodos[MST[i].origen].lon);
            ar[i].latDes = Double.parseDouble(InfoNodos[MST[i].destino].lat);
            ar[i].lonDes = Double.parseDouble(InfoNodos[MST[i].destino].lon);
            ar[i].distancia = MST[i].peso;

        }
        return ar;
    }
}


