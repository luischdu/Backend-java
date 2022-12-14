/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animacionciudad;

import ciudad.Ciudad;
import ciudad.ParametrosDibujo;
import ciudad.Posicion;
import ciudad.Sitio;
import java.util.ArrayList;
import java.util.List;
import vehiculo.Automovil;
import vehiculo.Deportivo;
import vehiculo.Furgon;
import vehiculo.Platon;
import vehiculo.Vehiculo;

/**
 * Hilo utilizado para iniciar un proceso independiente
 * @author Camiku
 */
public class Hilo extends Thread {
    private boolean parar;
    private PanelControles panelControles;
    
    public Hilo(PanelControles panelControles) {
        this.panelControles = panelControles;
    }

    /**
     * Inicializa la ejecución de la aplicación de forma independiente
     */
    @Override
    public void run() {
        Automovil automovil = new Automovil(new Posicion(), 5);
        Deportivo deportivo = new Deportivo(new Posicion(), 5);
        Platon camioneta = new Platon(new Posicion(), 3, 4);
        camioneta.llevar(Integer.parseInt(panelControles.getjTextFieldPlaton().getText()));
        Furgon furgon = new Furgon(new Posicion(2, 50), 3, 4);
        furgon.llevar(panelControles.getjTextFieldCarga().getText());

        List<Vehiculo> listaVehiculos = new ArrayList();

        if (panelControles.getjCheckBoxVehiculosCarga().isSelected()) {
            listaVehiculos.add(camioneta);
            listaVehiculos.add(furgon);
        }

          if (panelControles.getjCheckBoxVehiculosFamiliar().isSelected()) {
            listaVehiculos.add(automovil);
            listaVehiculos.add(deportivo);
        }
        
        List<Sitio> listaSitios = new ArrayList();
        for (int i = 0; i < ParametrosDibujo.NUMERO_SITIOS; i++) {
            listaSitios.add(new Sitio(new Posicion()));
        }

        Ciudad ciudad = new Ciudad(listaVehiculos, listaSitios);

        setParar(false);
        
        for (int i = 0; i < (Integer)panelControles.getjSpinnerIteraciones().getValue(); i++) {
            if (panelControles.getjRadioButtonAdelante().isSelected()) {
                ciudad.mover();
            }else if (panelControles.getjRadioButtonReversa().isSelected()){
                ciudad.moverReversa();
            }
            panelControles.getjTextArea().setText(ciudad.pintar());
            try {
                sleep((Integer) panelControles.getjSpinnerDormir().getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if(parar){
                i = ParametrosDibujo.ITERACIONES_CIUDAD;
            }
        }
        // Reactiva el boton de ejecución
        panelControles.getjButtonIniciar().setEnabled(true);
    
    }

    public boolean isParar() {
        return parar;
    }

    public void setParar(boolean parar) {
        this.parar = parar;
    }

    
    
}
