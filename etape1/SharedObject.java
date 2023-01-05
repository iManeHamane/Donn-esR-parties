import java.io.*;

public class SharedObject implements Serializable, SharedObject_itf {
	private int idObject;// idClient;
    public Object obj; 
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
	public SharedObject(Object obj, int ido){
		this.obj=obj;
		this.idObject=ido;

	}
	
	// invoked by the user program on the client node
	public void lock_read() {
		//si c'est cached, on ne fait pas appel au client
				synchronized(this){
					switch(lock){
						//case 0 : 
							//etat initial
						//	break;
						case 1 : 
							//de cached il devient taken
							lock =3;
							break;
						case 2 :
							//de cached il devient read taken et write cached
							lock=5;
							break;
						default : 	
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
	public void lock_write(){
		synchronized(this){
			switch(lock){
				case 0 : 
					//etat initial
					break;
				case 2 : 
					//de cached il devient taken
					lock =4;
					break;
				case 3 :
					//de cached il devient read taken et write cached
					lock=5;
					break;
				default : 	
					break;
			}
		}
	}

	// invoked by the user program on the client node
	public synchronized void unlock() {
		lock=0;
		notify();
	}


	// callback invoked remotely by the server
	public synchronized Object reduce_lock() {
		return null;//juste pour le test
	}

	// callback invoked remotely by the server
	public synchronized void invalidate_reader() {
	}

	public synchronized Object invalidate_writer() {
		return null; //juste pour le test!
	}
}
