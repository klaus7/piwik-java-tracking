package org.piwik;

import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Klaus Pfeiffer on 06.02.15.
 */
public class AsyncPiwikTracker extends SimplePiwikTracker {

	private static final Logger LOGGER = Logger.getLogger(SimplePiwikTracker.class.getName());


	LinkedList<URL> pending = new LinkedList<URL>();

	Runnable requestSender = new Runnable() {
		@Override
		public void run() {
			if (pending != null) {
				synchronized (this) {
					while (!pending.isEmpty()) {
						URL first = pending.pollFirst();
						try {
							sendRequest(first);
						} catch (PiwikException e) {
							LOGGER.log(Level.SEVERE, "Couldn't send request to piwik server.");
						}
					}
				}
			}
		}
	};


	public AsyncPiwikTracker(String apiUrl) throws PiwikException {
		super(apiUrl);
		startSender();
	}

	public AsyncPiwikTracker(int idSite, String apiUrl, HttpServletRequest request) throws PiwikException {
		super(idSite, apiUrl, request);
		startSender();
	}

	private void startSender() {
		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleWithFixedDelay(requestSender, 1, 1, TimeUnit.SECONDS);
	}

	public void queueRequest(URL destination) throws PiwikException {
		pending.add(destination);
	}
}
