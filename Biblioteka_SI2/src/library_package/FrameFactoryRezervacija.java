package library_package;

public class FrameFactoryRezervacija {

	public FrameRezervacijaKorisnik getFrame(UserWindow userWindow, String idRezervacije) {
		
		if(userWindow.getUserTypeString().equals("student")) {
			return new FrameRezervacijaStudent(userWindow, idRezervacije);
		}
		if(userWindow.getUserTypeString().equals("bibliotekar")) {
			return new FrameRezervacijaBibliotekar(userWindow, idRezervacije);
		}
		if(userWindow.getUserTypeString().equals("admin")) {
			return new FrameRezervacijaAdmin(userWindow, idRezervacije);
		}
		return null;
	}
	
}
