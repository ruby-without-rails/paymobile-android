package br.com.frmichetti.carhollics.android.model.compatibility;

import java.util.HashSet;
import java.util.Set;

public class Customer extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Long id;

    private User user;

    private String name;

    private long cpf;

    private long phone;

    private long mobilePhone;

    private Set<Vehicle> vehicles = new HashSet<Vehicle>();

    public Customer() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (name != null && !name.trim().isEmpty())
            result += "name: " + name;
        result += ", cpf: " + cpf;
        result += ", phone: " + phone;
        result += ", mobilePhone: " + mobilePhone;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) obj;
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

    public Set<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public void setVehicles(final Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

}