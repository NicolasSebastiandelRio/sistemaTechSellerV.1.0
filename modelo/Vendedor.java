package modelo;

import java.util.ArrayList;
import java.util.Calendar;

public class Vendedor {
	private int numLegajo;
	private String nombreCompleto;
	private Calendar fechaContratacion;
	private char turnoJornada;
	private ArrayList<Producto> ventas = new ArrayList<>();
	
	
	public Vendedor() {}


	public Vendedor(int numLegajo, String nombreCompleto, Calendar fechaContratacion, char turnoJornada,
			ArrayList<Producto> ventas) {
		super();
		this.numLegajo = numLegajo;
		this.nombreCompleto = nombreCompleto;
		this.fechaContratacion = fechaContratacion;
		this.turnoJornada = turnoJornada;
		this.ventas = ventas;
	}


	public int getNumLegajo() {
		return numLegajo;
	}


	public void setNumLegajo(int numLegajo) {
		this.numLegajo = numLegajo;
	}


	public String getNombreCompleto() {
		return nombreCompleto;
	}


	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}


	public Calendar getFechaContratacion() {
		return fechaContratacion;
	}


	public void setFechaContratacion(Calendar fechaContratacion) {
		this.fechaContratacion = fechaContratacion;
	}


	public char getTurnoJornada() {
		return turnoJornada;
	}


	public void setTurnoJornada(char turnoJornada) {
		this.turnoJornada = turnoJornada;
	}


	public ArrayList<Producto> getVentas() {
		return ventas;
	}


	public void setVentas(ArrayList<Producto> ventas) {
		this.ventas = ventas;
	}
	
	
}
