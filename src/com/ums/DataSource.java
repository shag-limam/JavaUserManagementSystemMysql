package com.ums;
//import com.ums.model.Role;
import com.ums.model.User;
import javafx.collections. FXCollections; import javafx.collections.ObservableList;
public class DataSource {

    private ObservableList<User> users = FXCollections.observableArrayList();
    public DataSource() {
       // Ajoutons quelques utilisateurs
     /*  users.add(new User("Dior", "Ndeye", "ndeye.dior@gmail.com", "775231245", null, Role.SIMPLE_USER));
        users.add(new User("Yaffa", "Mouhamed", "mohamed.yaffa@gmail.com", "773391555", null, Role.SIMPLE_USER));
        users.add(new User("Sow", "Djiby", "djiby. sow@gmail.com", "78321205", null, Role.ADMINISTRATEUR));
        users.add(new User("Fall", "Seydina", "seydina.fall@gmail.com", "705121980", null, Role.SIMPLE_USER));
        users.add(new User("Kona", "Chafaa", "chafaa.kona@gmail.com", "773251007", null, Role.ADMINISTRATEUR));
        users.add(new User("Tall", "Yacine", "yacine.tall@gmail.com", "780157896", null, Role.SIMPLE_USER));
        users.add(new User("SAME", "Ridwane", "ridwane.same@gmail.com", "761250067", null, Role.ADMINISTRATEUR));
        users.add(new User("SEME", "Mamadou", "mamadou.seme@gmail.com", "776356700", null, Role.ADMINISTRATEUR));
        users.add(new User("Dieng", "Mouhamed", "mouhamed.dieng@gmail.com", "708961209", null, Role.SIMPLE_USER));
        users.add(new User("Bahini", "Freddy", "freddy.bahini@gmail.com", "783361728", null, Role.SIMPLE_USER));
   */ }
    public void removeUser(User user) {
        users.remove(user);
    }
    public ObservableList<User> getUsers() {
        return this.users;
    }
	public void createUser(User user) {
		this.users.add(user) ;
		
	}
	public void userUpdate(User user) {
		int index = users.indexOf(user);
	    if (index != -1) {
	        users.set(index, user);
	    }
		
	}
	
}