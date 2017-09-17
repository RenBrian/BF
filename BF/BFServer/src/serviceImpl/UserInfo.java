package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class UserInfo {
	
	private ArrayList<String> nameList;
	private ArrayList<String> infoList;
	
	public UserInfo(){
		StringBuilder info = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("Info")));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


}
