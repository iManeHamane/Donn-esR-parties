import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.rmi.registry.*;
import java.net.*;

public class Client extends UnicastRemoteObject implements Client_itf {

	private static HashMap<Integer, SharedObject> objects;
	//private static int idClient;
	private static Server_itf server;

	//constructor of the client class
	public Client() throws RemoteException {
		super();
		try{
			server = (Server_itf)Naming.lookup("Server");

		}
		catch(Exception e){
			e.printStackTrace();
		}

		objects = new HashMap<Integer, SharedObject>();
		//idClient = server.getIdClient();
	}

	//public int getIdClient(){
	//	return idClient;
	//}


///////////////////////////////////////////////////
//         Interface to be used by applications
///////////////////////////////////////////////////

	// initialization of the client layer
	public static void init() {
		//initialization
		server = null;
		try{
			server = (Server_itf)Naming.lookup("Server");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		//vérification dune bonne initialisation ou pas 
		if ( server == null ){
			System.out.println("Serveur non trouvé !");
		}
		else {
			System.out.println("Client initialisé avec succès !");
		}
	}
	
	// lookup in the name server
	public static SharedObject lookup(String name) throws RemoteException{
		try{

			// recuperer l'objet
			int idObject = server.lookup(name);
			if (idObject != -1){ 
					SharedObject object= new SharedObject(null, idObject);
					//ajouter l'objet à la liste
					objects.put(idObject, object);
					return object;
			}
			else return null;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}		
	
	// binding in the name server
	public static void register(String name, SharedObject so) throws RemoteException{
		try{
		server.register(name, so.getIdObject());
		}
		catch(Exception e ){
			e.printStackTrace();
		
		}
		

	}

	// creation of a shared object
	public static SharedObject create(Object o) throws RemoteException {
		try{
			int i = server.create(o);
			SharedObject so = new SharedObject(o, i);
			objects.put(i, so);
			return so;
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
		try{
				return server.lock_read(id, new Client());
		}
		catch(Exception e ){
			e.printStackTrace();
		}
		//si le try ne marche pas
		return null;
	}

	// request a write lock from the server
	public static Object lock_write (int id) throws RemoteException {
		try{
				return server.lock_write(id, new Client());
		}
		catch(Exception e ){
			e.printStackTrace();
		}
		//si ca marche pas
		return null;	
	}

	// receive a lock reduction request from the server
	public Object reduce_lock(int id) throws RemoteException {
		try{
				SharedObject so = objects.get(id);
				if(so!= null){
					try{
						synchronized(so){
							so.reduce_lock();
							return so.getObject();
						}
					}
					catch (Exception e){
						e.printStackTrace();

					}
					return null;

				}
				return null;
		}
		catch(Exception e ){
			e.printStackTrace();
		}
		return null;	
	}


	// receive a reader invalidation request from the server
	public void invalidate_reader(int id) throws RemoteException 
	{
			SharedObject so = objects.get(id);
			if(so!= null){
				try{
					synchronized(so){
						so.invalidate_reader();
					}
				}
				catch (Exception e){
					e.printStackTrace();

				}
			}
			else System.out.println("erreur");
	
	}


	// receive a writer invalidation request from the server
	public Object invalidate_writer(int id) throws RemoteException {
		SharedObject so = objects.get(id);
			if(so!= null){
				try{
					synchronized(so){
						return so.invalidate_writer();
					}
				}
				catch (Exception e){
					e.printStackTrace();

				}
			}
			else System.out.println("erreur");
			return null;	
	}
}
