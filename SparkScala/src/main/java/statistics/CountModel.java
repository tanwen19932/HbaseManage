package statistics;

import java.io.Serializable;

public class CountModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String comeFrom;
	ERROR errorMessage;
	String language;
	String countTime;
	
	public CountModel(String comeFrom, ERROR errorMessage, String language, String countTime) {
		super();
		this.comeFrom = comeFrom;
		this.errorMessage = errorMessage;
		this.language = language;
		this.countTime = countTime;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final CountModel that = (CountModel) o;

		if (comeFrom != null ? !comeFrom.equals(that.comeFrom) : that.comeFrom != null) return false;
		if (errorMessage != that.errorMessage) return false;
		if (language != null ? !language.equals(that.language) : that.language != null) return false;
		return countTime != null ? countTime.equals(that.countTime) : that.countTime == null;

	}

	@Override
	public int hashCode() {
		int result = comeFrom != null ? comeFrom.hashCode() : 0;
		result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
		result = 31 * result + (language != null ? language.hashCode() : 0);
		result = 31 * result + (countTime != null ? countTime.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "CountModel{" +
				"comeFrom='" + comeFrom + '\'' +
				", errorMessage=" + errorMessage +
				", language='" + language + '\'' +
				", countTime=" + countTime +
				'}';
	}
	
}
