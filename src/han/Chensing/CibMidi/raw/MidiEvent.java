package han.Chensing.CibMidi.raw;

public class MidiEvent {
	private int deltaTime;
	private int track;
	private EventType eventType;
	private Object[] parameters;

	@SuppressWarnings("unused")
	public enum EventType{
		KeyUp(MidiDataReader.MIDI_EVENT_RELEASE),
		KeyDown(MidiDataReader.MIDI_EVENT_PRESS),
		KeyAfterTouch(MidiDataReader.MIDI_EVENT_KAT),
		Controller(MidiDataReader.MIDI_EVENT_CTRLER),
		ChangeInstrument(MidiDataReader.MIDI_EVENT_CGINST),
		ChannelOfKeyAfterTouch(MidiDataReader.MIDI_EVENT_KATCH),
		Slide(MidiDataReader.MIDI_EVENT_SLIDE),
		SystemCode(MidiDataReader.MIDI_EVENT_SYSCODE),
		Other(MidiDataReader.MIDI_EVENT_OTHER);

		int code;
		EventType(int code){
			this.code=code;
		}
		public int getCode() {
			return code;
		}
		public static EventType valueOf(int code){
			EventType[] values = values();
			for (EventType eventType:values) {
				if (eventType.code==code)
					return eventType;
			}
			return null;
		}
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public int getDeltaTime() {
		return deltaTime;
	}

	public void setDeltaTime(int deltaTime) {
		this.deltaTime = deltaTime;
	}

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}
}
