package br.com.compasso.cepms.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddressDTO {

	private String cepNumber;
	private String street;
	private String complements;
	private String neighborhood;
	private String city;
	private String state;

	public String getCepNumber() {
		return this.cepNumber;
	}

	public void setCepNumber(String cepNumber) {
		this.cepNumber = cepNumber;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getComplements() {
		return this.complements;
	}

	public void setComplements(String complements) {
		this.complements = complements;
	}

	public String getNeighborhood() {
		return this.neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String toString() {
		return "AddressDTO [cepNumber=" + this.cepNumber + ", street=" + this.street + ", complements="
				+ this.complements + ", neighborhood=" + this.neighborhood + ", city=" + this.city + ", state="
				+ this.state + "]";
	}
}
