import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;


public class Server extends UnicastRemoteObject implements Server_itf
{

    public Server () throws RemoteException {
        super();
    }
    @Override
    public int create(Object o) throws RemoteException {
        
        return new Random().nextInt(1024);
    }

    @Override
    public Object lock_read(int id, Client_itf client) throws RemoteException {
        
        return null;
    }

    @Override
    public Object lock_write(int id, Client_itf client) throws RemoteException {
        
        return null;
    }

    @Override
    public int lookup(String name) throws RemoteException {
        try {
        ServerObject s = (ServerObject) Naming.lookup(name);
        return s.getId();
        }
        catch(Exception e){ }
        return 0;
    }

    @Override
    public void register(String name, int id) throws RemoteException {
        
        
    }
    
}
