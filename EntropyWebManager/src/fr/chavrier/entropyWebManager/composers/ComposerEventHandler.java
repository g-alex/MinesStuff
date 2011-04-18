package fr.chavrier.entropyWebManager.composers;

public class ComposerEventHandler {
	
	private static EventSensitiveComposer domainComposerListerner;
	private static EventSensitiveComposer serverPoolComposerListerner;
	private static EventSensitiveComposer hostComposerListerner;
	private static EventSensitiveComposer imageStoreComposerListerner;
	private static EventSensitiveComposer vmComposerListerner;
	private static EventSensitiveComposer vmImageComposerListerner;
	
	public static void fireOnSelectNotification(EventSensitiveComposer c, Object o){
		if(c!=null){
			c.onSelect(o);
		}
	}

	public static void setDomainComposerListerner(
			EventSensitiveComposer d) {
		domainComposerListerner = d;
	}

	public static void setServerPoolComposerListerner(
			EventSensitiveComposer s) {
		serverPoolComposerListerner = s;
	}

	public static void setHostComposerListerner(
			EventSensitiveComposer h) {
		hostComposerListerner = h;
	}

	public static void setImageStoreComposerListerner(
			EventSensitiveComposer i) {
		imageStoreComposerListerner = i;
	}

	public static void setVmComposerListerner(EventSensitiveComposer v) {
		vmComposerListerner = v;
	}

	public static void setVmImageComposerListerner(
			EventSensitiveComposer v) {
		vmImageComposerListerner = v;
	}

	public static EventSensitiveComposer getDomainComposerListerner() {
		return domainComposerListerner;
	}

	public static EventSensitiveComposer getServerPoolComposerListerner() {
		return serverPoolComposerListerner;
	}

	public static EventSensitiveComposer getHostComposerListerner() {
		return hostComposerListerner;
	}

	public static EventSensitiveComposer getImageStoreComposerListerner() {
		return imageStoreComposerListerner;
	}

	public static EventSensitiveComposer getVmComposerListerner() {
		return vmComposerListerner;
	}

	public static EventSensitiveComposer getVmImageComposerListerner() {
		return vmImageComposerListerner;
	}

}
