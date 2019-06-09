package edu.fin.data;

public class Submit<T> {
	private T title;
	private T summary;
	private T coreWord;
	private T date;
	private T realSource;
	private T originSource;
	private T owner;
	
	public T getTitle() {
		return title;
	}
	public void setTitle (T title) {
		this.title = title;
	}
	public T getSummary() {
		return summary;
	}
	public void setSummary(T summary) {
		this.summary = summary;
	}
	public T getCoreWord() {
		return coreWord;
	}
	public void setCoreWord(T coreWord) {
		this.coreWord = coreWord;
	}
	public T getDate() {
		return date;
	}
	public void setDate(T date) {
		this.date = date;
	}
	public T getRealSource() {
		return realSource;
	}
	public void setRealSource(T realSource) {
		this.realSource = realSource;
	}
	public T getOriginSource() {
		return originSource;
	}
	public void setOriginSource(T originSource) {
		this.originSource = originSource;
	}
	public T getOwner() {
		return owner;
	}
	public void setOwner(T owner) {
		this.owner = owner;
	}
	
	
}
