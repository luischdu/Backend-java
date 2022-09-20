/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentimientosdegroot;
import java.util.Scanner;

/**
 *
 * @author luischdu
 */
public class SentimientosDeGroot {

    public static String groot(int n){
        String s="";  
        for (int i = 1; i < n; i++) {
            
                  if(i%2==0) s+="that I am ";
                  if(i%2!=0) s+="that I groot ";
              
            
          
        }
        return s;
            
       
        }
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        String str1 = ("I am "+groot(n)+"it" );
        System.out.println(str1);
       
    }
    
}