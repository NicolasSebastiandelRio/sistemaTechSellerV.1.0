package modelo;

import java.util.ArrayList;

public class Comprador {
	private int dni;
	private String nombreCompleto;
	private String correo;
	private String telefono;
	private ArrayList<Producto> compras=new ArrayList<>();
	
	
	public Comprador() {}


	public Comprador(int dni, String nombreCompleto, String correo, String telefono, ArrayList<Producto> compras) {
		super();
		this.dni = dni;
		this.nombreCompleto = nombreCompleto;
		this.correo = correo;
		this.telefono = telefono;
		this.compras = compras;
	}


	public int getDni() {
		return dni;
	}


	public void setDni(int dni) {
		this.dni = dni;
	}


	public String getNombreCompleto() {
		return nombreCompleto;
	}


	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public ArrayList<Producto> getCompras() {
		return compras;
	}


	public void setCompras(ArrayList<Producto> compras) {
		this.compras = compras;
	}
	
	
}
