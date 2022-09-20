/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventario;

/**
 *
 * @author luischdu
 */
public abstract class Vehiculo {
    /**
     * Speed of the Vehicle
     */
    protected double velocidad;
    /**
     * Number of passengers 
     */
    protected int pasajeros;
    
    /**
     * Creates a vehicle with the given number of passengers and speed
     * @param pasajeros Number of passengers
     * @param velocidad Speed of the Vehicle
     */
    public Vehiculo(int pasajeros, double velocidad) {
	this.velocidad = velocidad;
	this.pasajeros = pasajeros;
    }
    
    /**
     * Determines the position of the vehicle at the given time
     * @param tiempo Time 
     * @return Position of the vehicle at the given time
     */
    public int posicion( int tiempo ) {
	return (int)(tiempo*velocidad);
    }
    
    public void espacios(int espacios){
	for( int i=0;i<espacios;i++) System.out.print(' ');
    }
    
 
    public abstract void pintar( int posicion ); 
}
