import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;


public class Server extends UnicastRemoteObject implements Server_itf
{
    private HashMap<Integer, ServerObject> objects;
    private HashMap<String, Integer> names;
    private int id;

    public Server () throws RemoteException {
        super();

        objects = new HashMap<Integer, ServerObject>();
        names = new HashMap<String, Integer>();
        try{
            LocateRegistry.createRegistry(0);
            System.out.println("registry lancé sur le prot 0!");
            Naming.rebind("Server", this);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public int create(Object o) throws RemoteException {
        id++;
        ServerObject so = new ServerObject(id, o);
        objects.put(id, so);
        
        return id;
    }

    @Override
    public Object lock_read(int id, Client_itf client) throws RemoteException {
        synchronized(objects.get(id)){
            objects.get(id).lock_read(client);
            return objects.get(id).getCache();

        }
        return null;
    }

    @Override
    public Object lock_write(int id, Client_itf client) throws RemoteException {
        synchronized(objects.get(id)){
            objects.get(id).lock_write(client);
            return objects.get(id).getCache();
            
        }
        return null;
    }

    @Override
    public int lookup(String name) throws RemoteException {
       Integer i=names.get(name);
       return ((i!=null)?i:-1);
    }

    @Override
    public void register(String name, int id) throws RemoteException {
        names.put(name,id);

        
    }


    
}
