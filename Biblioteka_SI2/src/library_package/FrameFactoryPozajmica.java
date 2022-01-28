package library_package;

public class FrameFactoryPozajmica {
	
	public FramePozajmicaKorisnik getFrame(UserWindow userWindow, String idPozajmice) {
		
		if(userWindow.getUserTypeString().equals("admin")) {
			return new FramePozajmicaAdmin(userWindow, idPozajmice);
		}
		if(userWindow.getUserTypeString().equals("student")) {
			return new FramePozajmicaStudent(userWindow, idPozajmice);
		}
		if(userWindow.getUserTypeString().equals("bibliotekar")) {
			return new FramePozajmicaBibliotekar(userWindow, idPozajmice);
		}
		return null;
	}

}
