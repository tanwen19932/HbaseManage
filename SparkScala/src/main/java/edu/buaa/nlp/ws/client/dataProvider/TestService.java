package edu.buaa.nlp.ws.client.dataProvider;

public class TestService {
	public static void main(String[] args) {
		String aString =new DataServiceImplService().getDataServiceImplPort().fetchData("2016-01-01", "2016-12-31", "en");
		System.out.println(aString);
	}
}
