import java.io.*;

public class SharedObject implements Serializable, SharedObject_itf {
	private int idObject, idClient;
    private Object obj; 
	private static int lock;

	/* lock 
	 * 0 : NL
	 * 1 : RLC
	 * 2 : WLC
	 * 3 : RLT
	 * 4 : WLT
	 * 5 : RLT_WLC
	 */

	// getters and setters 
    public void setIdObject(int i){
        idObject=i;
    }
    public int getIdObject(){
        return idObject;
    }
    public void setObject(Object o){
        obj=o;
    }
    public Object getObject(){
        return obj;
    }

	//constructor
	public SharedObject(Object obj, int ido, int idc){
		this.obj=obj;
		this.idObject=ido;
		this.idClient = idc;

	}
	
	// invoked by the user program on the client node
	public void lock_read() {
				synchronized(this){
					switch(lock){
						case 0 : 
							//etat initial
							break;
						case 1 : 
							//de cached il devient taken
							lock =3;
							break;
						case 2 :
							//de cached il devient read taken et write cached
							lock=5;
							break;
						default : 	
							//les autres cas sont des cas d'erruer 
							//car il va etre deja en etat read lock taken
							break;
					}
				}
				while(lock!=3){
					try{
					obj = Client.lock_read(idObject);
					synchronized(this){
						if(obj!=null){
							lock=3;
						}
					}
					}
					catch(Exception e ){
						e.printStackTrace();
					}

				}

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
