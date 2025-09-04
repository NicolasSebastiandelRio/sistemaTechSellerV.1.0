package modelo;

import java.util.ArrayList;
import java.util.Calendar;

public abstract class Producto {
	protected String codigoSKU;
	protected String nombreProducto;
	protected double precio;
	protected String descripcion;
	protected Calendar fechaIngreso;
	protected InformacionDeStock ids;
    protected ArrayList<Vendedor> vendedores; 
	
	public Producto() {
        this.vendedores = new ArrayList<>();
	}


	public Producto(String codigoSKU, String nombreProducto, double precio, String descripcion, Calendar fechaIngreso,
			InformacionDeStock ids) {
		super();
		this.codigoSKU = codigoSKU;
		this.nombreProducto = nombreProducto;
		this.precio = precio;
		this.descripcion = descripcion;
		this.fechaIngreso = fechaIngreso;
		this.ids = ids;
	}


	public String getCodigoSKU() {
		return codigoSKU;
	}


	public void setCodigoSKU(String codigoSKU) {
		this.codigoSKU = codigoSKU;
	}


	public String getNombreProducto() {
		return nombreProducto;
	}


	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}


	public double getPrecio() {
		return precio;
	}


	public void setPrecio(double precio) {
		this.precio = precio;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Calendar getFechaIngreso() {
		return fechaIngreso;
	}


	public void setFechaIngreso(Calendar fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}


	public InformacionDeStock getIds() {
		return ids;
	}


	public void setIds(InformacionDeStock ids) {
		this.ids = ids;
	}
	
    public ArrayList<Vendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(ArrayList<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }
}
