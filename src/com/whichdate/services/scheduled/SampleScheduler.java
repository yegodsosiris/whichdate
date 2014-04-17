package com.whichdate.services.scheduled;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * This class is configured to run on a scheduled basis. 
 * 
 * @author dave.hampton
 *
 */
@Service
public class SampleScheduler {

	protected final Log logger = LogFactory.getLog(getClass());

	@Scheduled(fixedDelay=5000)
	public void canBeNamedAnything() {
		System.out.println("I am running.....");
	}


}
