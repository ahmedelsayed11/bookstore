package com.utilty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EGConstant {
	
	public final static String EG = "EG";
	
	public final static Map<String ,String> mapOfEGStates = new HashMap<String , String>(){

		/**
		 * 
		 */
		private static final long serialVersionUID = -6838236543781409566L;

		{
			put("Al" , "Alexendria");
			put("SH" , "Sharkia");
			put("MF" , "Monofia");
			put("GH" , "Gharbia");
			put("AS" , "Aswan");
			put("Lu" , "Luxor");
			put("DA" , "Dakhlia");
			put("KE" , "Kafr_Elsheikh");
			put("BA" , "AlBanhaexendria");
			put("ZG" , "Zagazig");
     	}
	};
			
	public static List<String> ListOfStatesCodes = new ArrayList<>(mapOfEGStates.keySet()) ;
	public static  List<String>  ListOfStatesNames = new ArrayList<>(mapOfEGStates.values()) ;
	
	
	
}
