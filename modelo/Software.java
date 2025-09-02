package modelo;

import java.util.Calendar;

public class Software extends Producto{
	private String plataformaCompatible;
    private TipoLicencia tipoLicencia; // El tipo de dato es el enum
	private String version;
	private long tamanioDescarga;//megabytes
	private boolean requiereDrivers;
	
	public Software() {}

    public Software(String codigoSKU, String nombreProducto, double precio, String descripcion,
            Calendar fechaIngreso, InformacionDeStock ids, String plataformaCompatible,
            TipoLicencia tipoLicencia, String version, long tamanioDescarga, boolean requiereDrivers) {
			super(codigoSKU, nombreProducto, precio, descripcion, fechaIngreso, ids);
			this.plataformaCompatible = plataformaCompatible;
			this.tipoLicencia = tipoLicencia;
			this.version = version;
			this.tamanioDescarga = tamanioDescarga;
			this.requiereDrivers = requiereDrivers;
}

	public Software(String codigoSKU, String nombreProducto, double precio, String descripcion, Calendar fechaIngreso,
			InformacionDeStock ids) {
		super(codigoSKU, nombreProducto, precio, descripcion, fechaIngreso, ids);
		// TODO Auto-generated constructor stub
	}

	public String getPlataformaCompatible() {
		return plataformaCompatible;
	}

	public void setPlataformaCompatible(String plataformaCompatible) {
		this.plataformaCompatible = plataformaCompatible;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getTamanioDescarga() {
		return tamanioDescarga;
	}

	public void setTamanioDescarga(long tamanioDescarga) {
		this.tamanioDescarga = tamanioDescarga;
	}

	public boolean isRequiereDrivers() {
		return requiereDrivers;
	}

	public void setRequiereDrivers(boolean requiereDrivers) {
		this.requiereDrivers = requiereDrivers;
	}

	public TipoLicencia getTipoLicencia() {
		return tipoLicencia;
	}

	public void setTipoLicencia(TipoLicencia tipoLicencia) {
		this.tipoLicencia = tipoLicencia;
	}
	
	
}
