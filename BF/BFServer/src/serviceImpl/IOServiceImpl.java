package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import service.IOService;

public class IOServiceImpl implements IOService{
	
	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		File f = new File(userId + "_" + fileName);
		try {
			FileWriter fw = new FileWriter(f, false);
			fw.write(file);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	
	@Override
	public String readFile(String userId, String fileName) {
		StringBuffer str = new StringBuffer("");
		File f = new File(userId + "_" + fileName);
		try {
			FileReader file = new FileReader(f);
			BufferedReader br = new BufferedReader(file);
			
			String line = null;
			
			while ((line = br.readLine()) != null) {
				str.append(line);
			}
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return str.toString();
	}

	@Override
	public String readFileList(String userId) {
		// TODO Auto-generated method stub
		return "OK";
	}
	
}
