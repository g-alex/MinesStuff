package fr.chavrier.entropyWebManager.serverPush;

import org.zkoss.lang.Threads;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.DesktopUnavailableException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zk.ui.util.GenericForwardComposer;

public class PerfReportServerPush {
	private static Pushable item;
	private static boolean firstTime = true;

	public static void setCurrentItem(Pushable i){
		item = i;
		item.update();
	}

	public static void start()
	throws InterruptedException {
		final Desktop desktop = Executions.getCurrent().getDesktop();
		if (!desktop.isServerPushEnabled()) {
			System.out.println("Server push not already started : starting");
			desktop.enableServerPush(true);
		}
		if(firstTime){
			new WorkingThread(desktop).start();
			firstTime = false;
		}
	}

	public static void stop() throws InterruptedException {
		final Desktop desktop = Executions.getCurrent().getDesktop();
		if (desktop.isServerPushEnabled()) {
			desktop.enableServerPush(false);
		} else {
			Messagebox.show("Already stopped");
		}
	}
	
	/*
	 * PRIVATE CLASS : WORKING THREAD
	 */
	
	
	private static class WorkingThread extends Thread {

        private Desktop desktop;
        
        public WorkingThread(Desktop d){
        	this.desktop = d;
        }
        
        public void run() {
            try {
                while (true) {
                	//System.out.println("working thread");
                	if(item!=null) {
//	                    if (desktop == null
//	                            || desktop.isServerPushEnabled()) {
//	                        desktop.enableServerPush(false);
//	                        return;
//	                    }
	                    Executions.activate(desktop);
	                    try {
	                       item.update();
	                    } finally {
	                        Executions.deactivate(desktop);
	                    }
                	}
	                Threads.sleep(1000);
                }
            } catch (DesktopUnavailableException ex) {
                System.out.println("The server push thread interrupted");
            } catch (InterruptedException e) {
                System.out.println("The server push thread interrupted");
            }
        }
    }
}
