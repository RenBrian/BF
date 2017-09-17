package serviceImpl;

public class UserPO {
	
	private String username;
	private String password;
	
	public UserPO(String name, String code){
		this.username = name;
		this.password = code;
	}
	
	public void setInfo(String name, String code){
		this.username = name;
		this.password = code;
	}
	
	public String getUserName(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}

}
