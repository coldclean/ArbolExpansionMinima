import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;

/**
 * Esta clase maneja todo lo relacionado con los datos de las ciudades
 */

public class DatosCiudades {

    /**
     * clase para crear un tipo de dato propio que almacene la infomracion de los nodos
     */
    static class Nodos {
        String nombre, nodoId, lat, lon;

        /**
         * Constructor para inicializar la clase con los argumentos recibidos
         * @param n el nombre de la ciudad
         * @param nodo id del nodo
         * @param latitud latitud de la ciudad
         * @param longitud longitud de la ciudad
         */
        public Nodos(String n, String nodo, String latitud, String longitud) {
            nombre = n;
            nodoId = nodo;
            lat = latitud;
            lon = longitud;
        }
    }

    private String archivo, direccion;
    private StringBuilder sb = new StringBuilder();
    private String respuesta = "";
    private int nCiudades;
    private NodeList ListaNodos;

    /**
     * Constructor para recibir la informacion de la ruta de archivo y la direccion de la api
     * @param ruta la ruta del archivo xml con los datos
     * @param url direccion de la API
     */

    DatosCiudades(String ruta, String url) {
        archivo = ruta;
        direccion = url;
    }

    /**
     * Con este metodo se obtinen los datos del servidor y se regresa la informacion en un arreglo
     * @return arreglo del tipo nodo con los datos de todas las ciudades en el archivo XML
     */

    public Nodos[] obtenerCiudades() {
        File entrada = new File(archivo);
        String vNodo = "", lat = "", lon = "", nombre = "";

        try {
            URL url = new URL(direccion);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setDoOutput(true);
            conexion.setDoInput(true);
            conexion.setInstanceFollowRedirects(false);
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/xml");
            OutputStream os = conexion.getOutputStream();
            TransformerFactory Transformador = TransformerFactory.newInstance();
            Transformer adaptador = Transformador.newTransformer();
            FileReader lectorArchivo = new FileReader(entrada);
            StreamSource fuente = new StreamSource(lectorArchivo);
            StreamResult resultado = new StreamResult(os);
            adaptador.transform(fuente, resultado);
            os.flush();
            os.close();
            InputStream stream = conexion.getInputStream();
            InputStreamReader lector = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(lector);

            String linea;
            while ((linea = br.readLine()) != null) {
                respuesta += linea;
            }
            br.close();
            conexion.disconnect();

        } catch (final Exception ei) {
            System.out.println(ei);
        }

        try {
            DocumentBuilderFactory armadorDoc = DocumentBuilderFactory.newInstance();
            DocumentBuilder creadorD = armadorDoc.newDocumentBuilder();
            Document doc = creadorD.parse(new InputSource(new StringReader(respuesta)));
            ListaNodos = doc.getElementsByTagName("node");
        } catch (Exception e) {
            e.printStackTrace();
        }

            nCiudades = ListaNodos.getLength();
            Nodos arregloNodos[] = new Nodos[nCiudades];
            int aristas = (nCiudades * (nCiudades - 1)) / 2;
            Kruskal.V = nCiudades;
            Kruskal.E = aristas;
            System.out.println(Kruskal.V + " " + Kruskal.E);

            for (int i = 0; i < nCiudades; i++) {
                Node nodoN = ListaNodos.item(i);
                vNodo = nodoN.getAttributes().getNamedItem("id").toString();
                lat = nodoN.getAttributes().getNamedItem("lat").toString();
                lon = nodoN.getAttributes().getNamedItem("lon").toString();
                NodeList hijos = nodoN.getChildNodes();
                for (int j = 0; j < hijos.getLength(); j++) {
                    Node nodoFinal = (Node) hijos.item(j);
                    if (nodoFinal.getNodeType() == Node.ELEMENT_NODE) {
                        Element fin = (Element) nodoFinal;
                        String key = new String(fin.getAttributes().getNamedItem("k").toString());
                        if (key.equals("k=\"name\"")) {
                            nombre = fin.getAttributes().getNamedItem("v").toString();
                        }
                    }
                }
                nombre = nombre.substring(3).replaceAll("\"", "");
                vNodo = vNodo.substring(4).replaceAll("\"", "");
                lat = lat.substring(5).replaceAll("\"", "");
                lon = lon.substring(5).replaceAll("\"", "");
                arregloNodos[i] = new Nodos(nombre, vNodo, lat, lon);
            }

        for (int i = 0; i < arregloNodos.length; i++) {

            System.out.println("Ciudad: " + arregloNodos[i].nombre + " Nodo:" + arregloNodos[i].nodoId + " Latitud:" + arregloNodos[i].lat + " Longitud:" + arregloNodos[i].lon);
        }

        return arregloNodos;
    }
}
