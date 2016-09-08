/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.model.compatibility;

public class Vehicle extends BaseModel {

	private static final long serialVersionUID = 1L;	

	private Long id;

	private Category category;

	private String brand;

	private String model;

	public Vehicle(){}	

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}
	

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (brand != null && !brand.trim().isEmpty())
			result += "brand: " + brand;
		if (model != null && !model.trim().isEmpty())
			result += ", model: " + model;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Vehicle)) {
			return false;
		}
		Vehicle other = (Vehicle) obj;
		if (getId() != null) {
			if (!getId().equals(other.getId())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}




}