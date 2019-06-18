package com.sibmed.domain.enums;

public enum TipoCategoria {
	RISCO_A(1,"RISCO A"),
	RISCO_B(2, "RISCO B"),
	RISCO_C(3, "RISCO C"),
	RISCO_D(4, "RISCO D"),
	RISCO_X(5, "RISCO X");
	
	private int cod;
	private String descricao;
	
	private TipoCategoria(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCategoria toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for(TipoCategoria x : TipoCategoria.values()) {
			if(cod.equals(x.getCod())){
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " +cod);
	}
}
