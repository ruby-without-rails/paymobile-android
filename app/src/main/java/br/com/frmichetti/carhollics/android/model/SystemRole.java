package br.com.frmichetti.carhollics.android.model;


public class SystemRole {

	private String name;
	
	@Deprecated
	public SystemRole() {
	}
	
	public SystemRole(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
