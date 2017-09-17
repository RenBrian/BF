//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;
import service.ExecuteService;
//import service.UserService;

/**
 * 
 * @author Ren
 * judge Ook / BF with content "Ook"
 * then execute the process.
 */
public class ExecuteServiceImpl implements ExecuteService {
	
	//test
	public static void main(String[] args) throws RemoteException{
		ExecuteServiceImpl esi = new ExecuteServiceImpl();
		/*
		 * ",.","A"
		 * ",+++++.","A"
		 * ",>++++++[<-------->-],,[<+>-]<.","4+3"
		 * "++++++++[>+++++++++[>+>+>+>+>+>+<<<<<<-]<-]++++++[>++++++[>>+>-<<<-]<-]>>>--->---->++++++>++>+++++++++++++<<<<<.>.>.>.>.>.",""
		 * ",>,,>++++++++[<------<------>>-]<<[>[>+>+<<-]>>[<<+>>-]<<<-]>>>++++++[<++++++++>-]<.","2*4"
		 * ",>,<.>.","AB"
		 * ",+++.","D"
		 */
		System.out.println(esi.execute(",.","!"));
	}
	
	//data for String code
	private char[] code;
	private byte[] para;
	private int indexOfParam = 0;
	//data for virtual BF
	private byte[] by = new byte[30000];
	private int index = 0;
	
	private int[] left,right;	
	
	/*
	 * check whether the code was legal & mark the []
	 */
	public boolean checkLegal(String code){
		
		//clean the space
		String newCode = code.replaceAll("\\s*", "");
		
		//illegal symbols
		for (int i = 0; i < newCode.length(); i++) {
			if (newCode.charAt(i) != '<'
				&& newCode.charAt(i) != '>' 
				&& newCode.charAt(i) != ',' 
				&& newCode.charAt(i) != '.'
				&& newCode.charAt(i) != '+'
				&& newCode.charAt(i) != '-'
				&& newCode.charAt(i) != '['
				&& newCode.charAt(i) != ']') {
				return false;
			}
		}
		
		//the number of [ and ]
		int n = 0;
		int l = 0;
		int r = 0;
		
		for (int i = 0; i < newCode.length(); i++) {
			switch (newCode.charAt(i)) {
			case '[':
				n ++;
				l ++;
				break;
			case ']':
				r ++;
				break;
			}
		}
		
		if (l != r) {
			return false;
		}
		
		//important!!! mark the position of []
		left = new int[n];
		right = new int[n];
		int Nl = 0, Nr = 0;
		for (int i = 0; i < newCode.length(); i++) {
			switch (newCode.charAt(i)) {
			case '[':
				left[Nl++] = i;
				Nr = Nl;
				break;
			case ']':
				for (int o = Nr; o > 0; o--){
					// a uninitialized integer array's elements are 0 by default. 
					if (right[o - 1] == 0){
						right[o - 1] = i;
						break;
					}
				}
				break;
			}
		}
		return true;
	}
	
	
	/**
	 * meets '[' and the value is 0 - jump forward
	 * @param i
	 * @return
	 */
	public int forward(int i){
		for (int j = 0; j < left.length; j++) {
			if (left[j] == i) {
				return right[j];
			}
		}
		return -1;
	}
	/**
	 * meets ']' and the value is not 0 - jump backward
	 * @param i
	 * @return
	 */
	public int backward(int i){
		for (int j = 0; j < right.length; j++) {
			if (right[j] == i) {
				return left[j];
			}
		}
		return -1;
	}
	
	

	/**
	 * 请实现该方法
	 * @param code, param
	 * @throws RemoteException 
	 * @return
	 */
	@Override
	public String execute(String code, String param) throws RemoteException {
		//change Ook 2 bf
		if (isOok(code)) {
			code = transCode(code);
		}
		
		//return 
		String result = "";
		//deal with the code
		if (!checkLegal(code))
			throw new RemoteException("Illegal Code!");
		else
			this.code = code.toCharArray();
		
		//deal with the param
		this.para = param.getBytes();
		
		for (int i = 0; i < this.code.length; i++) {
			switch(this.code[i]){
			case '>':
				index = (index == 29999) ? 0 : index + 1;
				break;
			case '<':
				index = (index == 0) ? 29999 : index - 1;
				break;
			case '+':
				by[index] = (byte) ((by[index] == 255) ? 0 : by[index] + 1);
				break;
			case '-':
				by[index] = (byte) ((by[index] == 0) ? 255 : by[index] - 1);
				break;
			case '.':
				result += (char)by[index];
				break;
			case ',':
				by[index] = this.para[indexOfParam++];
				break;
			case '[':{
				if (by[index] == 0) {
					i = forward(i);
					
					if (i == -1) {
						throw new RemoteException("Unable to find parallel ]");
					}
				}
				break;
			}
			case ']':{
				if (by[index] != 0) {
					i = backward(i);
					
					if (i == -1) {
						throw new RemoteException("Unable to find parallel [");
					}
				}
				break;
			}
			}
				
		}
		return result;
	}
	
	/**
	 * To judge whether the String if Ook or bf
	 * @param code
	 * @return
	 */
	public boolean isOok(String code){
		if (code.contains("Ook")) {
			return true;
		}
		return false;
	}
	
	/**
	 * @author Ren
	 * @param oriCode
	 * @return
	 * @throws RemoteException 
	 */
	public String transCode(String oriCode) throws RemoteException{
		
		//clean
		String newCode = oriCode.replaceAll("Ook","").replaceAll(" ", "");
//		System.out.println(newCode);
		String transCode = "";
		
		for (int i = 0; i < newCode.length(); i = i + 2) {
			String tem = newCode.charAt(i) + "" + newCode.charAt(i + 1);
			switch (tem){
			case ".?":
				transCode += ">";
				break;
			case "?.":
				transCode += "<";
				break;
			case "..":
				transCode += "+";
				break;
			case "!!":
				transCode += "-";
				break;
			case "!.":
				transCode += ".";
				break;
			case ".!":
				transCode += ",";
				break;
			case "!?":
				transCode += "[";
				break;
			case "?!":
				transCode += "]";
				break;
			default:
				throw new RemoteException("Illegal code!");
			}
		}
		
		return transCode;
	}
		
}
