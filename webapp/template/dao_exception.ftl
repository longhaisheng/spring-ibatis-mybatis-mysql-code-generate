package ${exceptionPackageName};

public class DAOException extends Exception{
	
	private static final long serialVersionUID = 5122280185969230388L;

	public DAOException() {
		super();
	}

	public DAOException(String msg) {
		super(msg);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

	public DAOException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
