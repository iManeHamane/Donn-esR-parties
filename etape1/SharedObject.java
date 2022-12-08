import java.io.*;

public class SharedObject implements Serializable, SharedObject_itf {
	private int id;
    private Object obj; 

    public void setId(int i){
        id=i;
    }
    public int getId(){
        return id;
    }
    public void setObject(Object o){
        obj=o;
    }
    public Object getObject(){
        return obj;
    }
	
	// invoked by the user program on the client node
	public void lock_read() {
	}

	// invoked by the user program on the client node
	public void lock_write() {
	}

	// invoked by the user program on the client node
	public synchronized void unlock() {
	}


	// callback invoked remotely by the server
	public synchronized Object reduce_lock() {
	}

	// callback invoked remotely by the server
	public synchronized void invalidate_reader() {
	}

	public synchronized Object invalidate_writer() {
	}
}
