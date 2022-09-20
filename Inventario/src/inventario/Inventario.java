/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventario;
import java.util.Scanner;
/**
 *
 * @author luischdu
 */
public class Inventario {
    
    public void procesarComandos(){
        
        Scanner sc = new Scanner(System.in);
        String comando[] = new String[1];
        String bandera = "verdadero";
        while (bandera != "falso"){
            comando=sc.nextLine().split("&");
            if(comando[0].equals("1")){
                Particular p = new Particular();
                
            }
        }
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        new Inventario().procesarComandos():
    }
    
}
