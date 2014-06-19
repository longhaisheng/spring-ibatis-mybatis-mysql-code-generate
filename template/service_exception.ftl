package ${exceptionPackageName};

public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = -183070443245145777L;

	public ServiceException() {
    	super();
    }
    
    public ServiceException(String message) {
    	super(message);
    }
    
    public ServiceException(Throwable cause) {
    	super(cause);
    }
    
    public ServiceException(String message, Throwable cause) {
    	super(message, cause);
    }
}
