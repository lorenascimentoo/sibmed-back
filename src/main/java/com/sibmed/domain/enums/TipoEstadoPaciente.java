package com.sibmed.domain.enums;

public enum TipoEstadoPaciente {
	GRAVIDEZ(1,"GRAVIDEZ"),
	LACTANTE(2, "LACTANTE");
	
	private int cod;
	private String descricao;
	
	private TipoEstadoPaciente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoEstadoPaciente toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for(TipoEstadoPaciente x : TipoEstadoPaciente.values()) {
			if(cod.equals(x.getCod())){
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " +cod);
	}
}
