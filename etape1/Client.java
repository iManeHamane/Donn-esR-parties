import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;
import java.net.*;

public class Client extends UnicastRemoteObject implements Client_itf {

	public Client() throws RemoteException {
		super();
	}


///////////////////////////////////////////////////
//         Interface to be used by applications
///////////////////////////////////////////////////

	// initialization of the client layer
	public static void init() {
	}
	
	// lookup in the name server
	public static SharedObject lookup(String name) throws RemoteException{
		try{
			return (SharedObject)Naming.lookup(name);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}		
	
	// binding in the name server
	public static void register(String name, SharedObject_itf so) throws RemoteException{
		try{
		Naming.rebind(name,(SharedObject)so);
		}
		catch(Exception e ){
			e.printStackTrace();
		
		}
		

	}

	// creation of a shared object
	public static SharedObject create(Object o) throws RemoteException {
		try{
			SharedObject s = new SharedObject(o);
			return s;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
/////////////////////////////////////////////////////////////
//    Interface to be used by the consistency protocol
////////////////////////////////////////////////////////////

	// request a read lock from the server
	public static Object lock_read(int id) throws RemoteException{
	}

	// request a write lock from the server
	public static Object lock_write (int id) throws RemoteException {
	}

	// receive a lock reduction request from the server
	public Object reduce_lock(int id) throws RemoteException {
	}


	// receive a reader invalidation request from the server
	public void invalidate_reader(int id) throws RemoteException {
	}


	// receive a writer invalidation request from the server
	public Object invalidate_writer(int id) throws RemoteException {
	}
}
