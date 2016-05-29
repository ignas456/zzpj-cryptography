package pl.zzpj.cryptography.des.exceptions;

public class InvalidKeyException extends Exception {

	private static final long serialVersionUID = -5629270387459133507L;

	public InvalidKeyException() {
	    super();
	}

	public InvalidKeyException(String message) {
	    super(message);
	}

	public InvalidKeyException(String message, Throwable cause) {
	    super(message, cause);
	}

	public InvalidKeyException(Throwable cause) {
	    super(cause);
	}
}
