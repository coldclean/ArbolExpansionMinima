import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * funcion que genera e Jframe que genera el mapa
 */
public class Mapa extends JFrame implements JMapViewerEventListener{

    private static final long serialVersionUID = 1L;
    private final JMapViewerTree treeMap;
    private final JLabel zoomLabel;
    private final JLabel zoomValue;
    private final JLabel mperpLabelName;

    /**
     * Constructor de la clase que inicializa los elementos del Jframe
     * @param arbol el arbol generado por la clase Kruskal
     * @param ciudades las ciudades obtenidas en la clase Datos ciudades
     */

    public Mapa(Kruskal.Arbol arbol[], DatosCiudades.Nodos ciudades[]) {

        super("Arbol de Expansion Minima");
        setSize(400, 400);
        treeMap = new JMapViewerTree("Ciudades");

        map().addJMVListener(this);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();
        JPanel helpPanel = new JPanel();

        mperpLabelName = new JLabel("Metros/Pixeles: ");

        zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JLabel(String.format("%s", map().getZoom()));

        add(panel, BorderLayout.NORTH);
        add(helpPanel, BorderLayout.SOUTH);
        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(panelBottom, BorderLayout.SOUTH);
        JLabel helpLabel = new JLabel("Usa el clic derecho para mover el mapa,\n "
                + "doble click izquiero o la rueda del mouse para el zoom.");
        helpPanel.add(helpLabel);
        JButton button = new JButton("Buscar ciudades");
        button.addActionListener(new ActionListener() {
            /**
             * agrega el Listener para el boton buscar ciudades
             * @param e el evento que recibe
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Automata marcoAutomata = new Automata(ciudades);
                marcoAutomata.setVisible(true);
            }
        });
        JButton mostrarArbol = new JButton("Mostrar Arbol");
        mostrarArbol.addActionListener(new ActionListener() {
            /**
             * se agrega el Listener para el boton Mostrar Arbol
             * @param e evento recibido
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String cadena = new String("");
                double total = 0;
                if (arbol.length == (ciudades.length-1)) {
                    for (int i = 0; i < arbol.length; i++) {
                        cadena = cadena + arbol[i].ciudadO + " - " + arbol[i].ciudadD + " Distancia: " + ((float) arbol[i].distancia) + "\n";
                        total = total + arbol[i].distancia;
                    }
                    cadena = cadena + " Distancia total del Arbol de Expansion Minima es: " + ((float) total);
                }else{
                    cadena = "No se puede Mostrar el arbol porque no cumple la condicion nodos - 1";
                }
                JOptionPane.showMessageDialog(null,cadena,"Arbol",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        final JCheckBox ocultarNombres = new JCheckBox("Mostrar/Esconder de Nombres Ciudades");
        ocultarNombres.setSelected(map().getMapMarkersVisible());
        ocultarNombres.addActionListener(new ActionListener() {
            /**
             * agrega el Listener para ocutarNombres
             * @param e el evento que recibe
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setMapMarkerVisible(ocultarNombres.isSelected());
            }
        });
        panelBottom.add(ocultarNombres);
        final JCheckBox listadoCiudades = new JCheckBox("Listado de Ciudades");
        listadoCiudades.addActionListener(new ActionListener() {
            /**
             * agrega el Listener para el Checkobox lisatado de ciudades
             * @param e el evento que recibe
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                treeMap.setTreeVisible(listadoCiudades.isSelected());
            }
        });
        panelBottom.add(listadoCiudades);
        panelBottom.add(button);
        panelBottom.add(mostrarArbol);

        add(treeMap, BorderLayout.CENTER);

        for(int i = 0 ; i<ciudades.length;i++){
            Layer etiqueta = treeMap.addLayer(ciudades[i].nombre);
            map().addMapMarker(new MapMarkerDot(etiqueta, ciudades[i].nombre, Double.parseDouble(ciudades[i].lat), Double.parseDouble(ciudades[i].lon)));
        }

        for(int i=0;i<arbol.length-1;i=i+2){
            MapPolygon bermudas = new MapPolygonImpl("Arbol", c( arbol[i].latOri,arbol[i].lonOri), c( arbol[i].latDes,arbol[i].lonDes) ,c( arbol[i+1].latOri,arbol[i+1].lonOri), c( arbol[i+1].latDes,arbol[i+1].lonDes));
            map().addMapPolygon(bermudas);
        }
        map().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    map().getAttribution().handleAttribution(e.getPoint(), true);
                }
            }
        });
    }

    private JMapViewer map() {
        return treeMap.getViewer();
    }
    private static Coordinate c(double lat, double lon) {
        return new Coordinate(lat, lon);
    }

    /**
     * agrega los Listener para los elementos del zoom
     * @param command evento que recibe el metodo
     */

    @Override
    public void processCommand(JMVCommandEvent command) {
        if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
                command.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
        }
    }
}


