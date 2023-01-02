public interface Client_itf extends java.rmi.Remote {
	public Object reduce_lock(int id) throws java.rmi.RemoteException;
	public void invalidate_reader(int id) throws java.rmi.RemoteException;
	public Object invalidate_writer(int id) throws java.rmi.RemoteException;
	//public int getIdClient() throws java.rmi.RemoteException;
	//to soufyane : j'ai besoin de cette méthode pour respecter les param d'une méthode
	//Mais je ss pas si j'ai le droit de changer cette interface ou pas faut qu'on demande.

}
