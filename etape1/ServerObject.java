import java.util.ArrayList;

public class ServerObject {
    private int id;
    private Object obj; 
    private ArrayList<Client_itf> clients;
    private int lock; 
    private Object cache;


    public ServerObject(int id, Object obj){
        this.id=id;
        this.obj=obj;
        lock=0; //NL
        clients = new ArrayList<Client_itf>();
        cache=obj;
    }

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
    public ArrayList<Client_itf> getClients(){
        return clients;
    }

    public void lock_read(Client_itf client){
        switch(lock){
        case 0 : //NL
            clients.add(client);
        case 3 : //RLT
            clients.add(client);
        case 4 : //WLT
            wait(clients);

        }

    }
     void wait(ArrayList<Client_itf> cl){
        for( Client_itf c : cl ){
            try{
                Object o = c.reduce_lock(id,c.getIdClient()); //la methode dans l enoncé n'a qu'un par et moi g besoin de 2
            }
        }
     }
    
    
}
