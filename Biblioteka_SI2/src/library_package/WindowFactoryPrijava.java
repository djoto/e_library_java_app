package library_package;

public class WindowFactoryPrijava {
	
	public UserWindow getWindowToShow(int idKorisnika, String tipKorisnika) {
		
		if(tipKorisnika.equals("admin")) {
			return new WindowAdmin(idKorisnika);
		}
		if(tipKorisnika.equals("student")) {
			return new StudentWindow(idKorisnika);
		}
		if(tipKorisnika.equals("bibliotekar")) {
			return new WindowBibliotekar(idKorisnika);
		}
		
		return null;
	}

}
