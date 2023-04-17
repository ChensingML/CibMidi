package han.Chensing.CibMidi.raw;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MidiChunk {
	private MidiDataReader.MTrkChunk chunkHead;
	private final List<MidiEvent> events;
	public MidiChunk() {
		this.events=new ArrayList<>();
	}
	
	public List<MidiEvent> getEvents() {
		return events;
	}
	public MidiDataReader.MTrkChunk getChunkHead() {
		return chunkHead;
	}
	void setChunkHead(MidiDataReader.MTrkChunk chunkHead) {
		this.chunkHead = chunkHead;
	}
}
