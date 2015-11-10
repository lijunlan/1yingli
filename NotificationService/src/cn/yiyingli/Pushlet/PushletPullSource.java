package cn.yiyingli.Pushlet;

import java.io.Serializable;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;

public class PushletPullSource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -809514704070850299L;

	static public class PingEventPullSource extends EventPullSource {
		public long getSleepTime() {
			//System.out.println("---00---");
			return 3000;
		}

		public Event pullEvent() {
			//System.out.println("123321");
			Event event = Event.createDataEvent("/yiyingli/notifi");
			event.setField("data", "heart beat\n");
			return event;
		}
	}

}
