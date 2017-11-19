import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * clase para crear la interfaz virtual que hara la busqueda de los caracteres
 */
public class Automata extends JFrame {
    JFrame Automata = new JFrame();
    String str;
    int pos;

    /**
     * constructor de la clase que recibe las ciudades e inicializa los elementos graficos
     * @param ciudades arreglo del tipo nodo con la informacion de la ciudad
     */

    public Automata(DatosCiudades.Nodos ciudades[])
    {
        super("Buscar Automata");
        this.setSize(300,500);
        this.setLayout(new FlowLayout());
        JLabel textoEt = new JLabel("Ingrese Palabra buscar");
        JTextPane result = new JTextPane();
        JTextField texto = new JTextField(10);
        textoEt.setSize(150,80);
        result.setSize(250,350);
        result.setFont(new Font("Arial", Font.BOLD, 12));
        result.setBackground(this.getBackground());
        for(int i=0;i<ciudades.length;i++)
            result.setText(result.getText() + "" + ciudades[i].nombre+"\n");

        this.add(textoEt);
        this.add(texto);
        this.add(result);

        texto.getDocument().addDocumentListener(new DocumentListener() {
            /**
             * meotodo para establecer las acciones a realizar cuando se ingresa el valor de texto
             * @param e evento que lanza el metodo
             */
            @Override
            public void insertUpdate(DocumentEvent e) {
                //showMessageDialog(null, "actualizo");
                result.setText("");
                for (int i = 0; i < ciudades.length; i++) {
                    str = ciudades[i].nombre.toLowerCase();
                    pos = str.indexOf(texto.getText().toLowerCase());
                    if (pos!=-1)
                    result.setText(result.getText() + "" + ciudades[i].nombre+"\n");
                }
            }
            /**
             * meotodo para establecer las acciones a realizar cuando se borra el valor de texto
             * @param e evento que recibe DocumentListener
             */
            @Override
            public void removeUpdate(DocumentEvent e) {
                result.setText("");
                for (int i = 0; i < ciudades.length; i++) {
                    str = ciudades[i].nombre.toLowerCase();
                    pos = str.indexOf(texto.getText().toLowerCase());
                    if (pos!=-1)
                        result.setText(result.getText() + "" + ciudades[i].nombre+"\n");
                }
            }
            /**
             * meotodo para establecer las acciones a realizar cuando se cambia el valor de texto
             * @param e evento que recibe DocumentListener
             */
            @Override
            public void changedUpdate(DocumentEvent e) {
                result.setText("");
                for (int i = 0; i < ciudades.length; i++) {
                    str = ciudades[i].nombre.toLowerCase();
                    pos = str.indexOf(texto.getText().toLowerCase());
                    if (pos!=-1)
                        result.setText(result.getText() + "" + ciudades[i].nombre+"\n");
                }
            }
        });




    }
}

