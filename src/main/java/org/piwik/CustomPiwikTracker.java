/**
 * Piwik - Open source web analytics
 * 
 * @license released under BSD License http://www.opensource.org/licenses/bsd-license.php
 * @link http://piwik.org/docs/tracking-api/
 *
 * @category Piwik
 * @package PiwikTracker
 */
package org.piwik;

import java.util.logging.Logger;

/**
 * This class can be used to track custom events not restricted to website tracking.
 *
 * @author Klaus Pfeiffer
 */
public class CustomPiwikTracker extends SimplePiwikTracker {

	private static final Logger LOGGER = Logger.getLogger(CustomPiwikTracker.class.getName());

	public CustomPiwikTracker(final int idSite, String apiUrl) throws PiwikException {
		super(idSite, apiUrl, null);
	}

}
