package library_package;


public class FrameFactoryDelo {
	
	public FrameDeloKorisnik getFrame(UserWindow userWindow, String idDela) {
		
		String userType = userWindow.getUserTypeString();
		
		if (userType == null){
			return null; 
		}
		if (userType.equals("student")) {
			return new FrameDeloStudent(userWindow, idDela);
		}
		if (userType.equals("bibliotekar")) {
			return new FrameDeloBibliotekar(userWindow, idDela);
		}
		if (userType.equals("admin")) {
			return new FrameDeloAdmin(userWindow, idDela);
		}
		
		return null;
		
		
	}

}
