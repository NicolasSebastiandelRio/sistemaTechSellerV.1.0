package excepciones;

public class EntidadYaExistenteException extends Exception{

	public EntidadYaExistenteException(String mensaje) {
		super(mensaje);
	}
	//se lanzara cuando un usuario intente ingresar prod
	// con codigo SKU existente
}
