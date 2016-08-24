package br.com.frmichetti.carhollics.android.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Service extends MyEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private int version;

	private String name;

	private String description;

	private String observation;

	private long duration;

	private BigDecimal price;

    private transient byte[] thumbnail;

    public Service() {
		this.id = 0L;
	}

    public Service(String name, BigDecimal price) {
        this();
        this.name = name;
        this.price = price;
    }

    public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Service)) {
			return false;
		}
		Service other = (Service) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (name != null && !name.trim().isEmpty())
			result += "name: " + name;
		if (description != null && !description.trim().isEmpty())
			result += ", description: " + description;
		if (observation != null && !observation.trim().isEmpty())
			result += ", observation: " + observation;
		result += ", duration: " + duration;
		return result;
	}

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public byte[] getThumbnail() {
		return thumbnail;
	}
}