package edu.buaa.nlp.duplicate;

public interface IDupDetector<T> {
	public String isDup(T param);
	public boolean saveDup(T param);
}
