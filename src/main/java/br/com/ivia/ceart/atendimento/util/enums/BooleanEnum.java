package br.com.ivia.ceart.atendimento.util.enums;

import java.util.Arrays;
import java.util.List;

public enum BooleanEnum {
	SIM("Sim"), NAO("Não");
	
	private String label;

	private BooleanEnum(String label){
		this.label = label;
	}
		
	public static BooleanEnum booleanToEnum(boolean valor){
		if (valor)
			return BooleanEnum.SIM;
		else
			return BooleanEnum.NAO;
	}
	
	public static boolean enumToBoolean(BooleanEnum valor){	
		return (valor == BooleanEnum.SIM);
	}
	
	public static List<BooleanEnum> getItens(){
		return Arrays.asList(BooleanEnum.values());
	}

}
