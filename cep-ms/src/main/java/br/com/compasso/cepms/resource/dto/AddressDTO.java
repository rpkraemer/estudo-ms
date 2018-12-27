package br.com.compasso.cepms.resource.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public class AddressDTO {

	private String cepNumber;

	private String street;

	private String complements;

	private String neighborhood;

	private String city;

	private String state;

	public String getCepNumber() {
		return cepNumber;
	}

	public void setCepNumber(String cepNumber) {
		this.cepNumber = cepNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getComplements() {
		return complements;
	}

	public void setComplements(String complements) {
		this.complements = complements;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "AddressDTO [cepNumber=" + cepNumber + ", street=" + street + ", complements=" + complements
				+ ", neighborhood=" + neighborhood + ", city=" + city + ", state=" + state + "]";
	}
}
