package modelo;

public class InformacionDeStock {
	private int cantidadDisponible;
	private String ubicacionAlmacen;
	private int stockMinimo;
	
	public InformacionDeStock() {}

	public InformacionDeStock(int cantidadDisponible, String ubicacionAlmacen, int stockMinimo) {
		super();
		this.cantidadDisponible = cantidadDisponible;
		this.ubicacionAlmacen = ubicacionAlmacen;
		this.stockMinimo = stockMinimo;
	}

	public int getCantidadDisponible() {
		return cantidadDisponible;
	}

	public void setCantidadDisponible(int cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}

	public String getUbicacionAlmacen() {
		return ubicacionAlmacen;
	}

	public void setUbicacionAlmacen(String ubicacionAlmacen) {
		this.ubicacionAlmacen = ubicacionAlmacen;
	}

	public int getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}
	
	
}
