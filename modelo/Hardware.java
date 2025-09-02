package modelo;

import java.util.Calendar;

public class Hardware extends Producto {
	private String tipoComponente;
	private int garantiaMeses;
	private double pesoGramos;
	private boolean requiereInstalacion;
	
	
	public Hardware(String codigoSKU, String nombreProducto, double precio, String descripcion,
	                Calendar fechaIngreso, InformacionDeStock ids, String tipoComponente, 
	                int garantiaMeses, double pesoGramos, boolean requiereInstalacion) {
	    super(codigoSKU, nombreProducto, precio, descripcion, fechaIngreso, ids);
	    this.tipoComponente = tipoComponente;
	    this.garantiaMeses = garantiaMeses;
	    this.pesoGramos = pesoGramos;
	    this.requiereInstalacion = requiereInstalacion;
	}

	public Hardware() {
	    super();
	}


	public Hardware(String codigoSKU, String nombreProducto, double precio, String descripcion, Calendar fechaIngreso,
			InformacionDeStock ids) {
		super(codigoSKU, nombreProducto, precio, descripcion, fechaIngreso, ids);
		// TODO Auto-generated constructor stub
	}


	public String getTipoComponente() {
		return tipoComponente;
	}


	public void setTipoComponente(String tipoComponente) {
		this.tipoComponente = tipoComponente;
	}


	public int getGarantiaMeses() {
		return garantiaMeses;
	}


	public void setGarantiaMeses(int garantiaMeses) {
		this.garantiaMeses = garantiaMeses;
	}


	public double getPesoGramos() {
		return pesoGramos;
	}


	public void setPesoGramos(double pesoGramos) {
		this.pesoGramos = pesoGramos;
	}


	public boolean isRequiereInstalacion() {
		return requiereInstalacion;
	}


	public void setRequiereInstalacion(boolean requiereInstalacion) {
		this.requiereInstalacion = requiereInstalacion;
	}
	
	
}
