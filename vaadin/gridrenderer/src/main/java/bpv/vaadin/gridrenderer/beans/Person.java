package bpv.vaadin.gridrenderer.beans;

public class Person {
	private int id;
	private String name;
	private boolean duplicate;
	private boolean primary;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isDuplicate() {
		return duplicate;
	}
	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}
	public boolean isPrimary() {
		return primary;
	}
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	
	public static Person createNew(int id){
		Person newPerson = new Person();
		newPerson.id = id;
		newPerson.name = "Paul Bala " + id;
		newPerson.duplicate = true;
		newPerson.primary = id == 1;
		
		return newPerson;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CPSPerson [id=" + id + ", name=" + name + ", duplicate=" + duplicate + ", primary=" + primary + "]";
	}
	
	
}
