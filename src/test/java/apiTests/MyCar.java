package apiTests;

import java.io.Serializable;

public class MyCar implements Serializable{

	/**
	 * 
	 */
	private String model;
	private String topSpeed;
	private transient String owner;
	
	
	public MyCar(String model, String topSpeed) {
		this.model = model;
		this.topSpeed = topSpeed;
	}
	public MyCar() {
	}
	
	@Override
	public String toString() {
		return this.model + " " + this.topSpeed;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the topSpeed
	 */
	public String getTopSpeed() {
		return topSpeed;
	}

	/**
	 * @param topSpeed the topSpeed to set
	 */
	public void setTopSpeed(String topSpeed) {
		this.topSpeed = topSpeed;
	}

}
