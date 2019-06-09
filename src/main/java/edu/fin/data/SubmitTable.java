package edu.fin.data;

public class SubmitTable<T>{
	private T title;
	private T serial;
	private T dataType;
	private T informations;
	private T infoNumber;

	
	public T getTitle() {
		return title;
	}
	public void setTitle(T title) {
		this.title = title;
	}
	public T getSerial() {
		return serial;
	}
	public void setSerial(T serial) {
		this.serial = serial;
	}
	public T getDataType() {
		return dataType;
	}
	public void setDataType(T dataType) {
		this.dataType = dataType;
	}
	public T getInformations() {
		return informations;
	}
	public void setInformations(T informations) {
		this.informations = informations;
	}
	public T getInfoNumber() {
		return infoNumber;
	}
	public void setInfoNumber(T infoNumber) {
		this.infoNumber = infoNumber;
	}
}
