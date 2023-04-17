package han.Chensing.CibMidi.raw;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MidiDataRaw {
	
	private final List<MidiChunk> chunks;
	private MidiFormat format;
	private int tracks;
	private int baseTime;

	MidiDataRaw() {
		this.chunks=new ArrayList<>();
	}

	@SuppressWarnings("unused")
	public enum MidiFormat{
		SingleTrack(0x0000),
		MultiTracks(0x0001),
		MultiTracksWithoutSynchronization(0x0002);
		private final short code;
		MidiFormat(int code) {
			this.code=(short)code;
		}
		public short getCode() {
			return code;
		}
		public static MidiFormat valueOfCode(short code) {
			MidiFormat[] formats=values();
			for(MidiFormat format:formats) {
				if (format.code==code)
					return format;
			}
			return null;
		}
	}
	
	public List<MidiChunk> getChunks() {
		return chunks;
	}
	public MidiFormat getFormat() {
		return format;
	}
	void setFormat(MidiFormat format) {
		this.format = format;
	}
	public int getTracks() {
		return tracks;
	}
	void setTracks(int tracks) {
		this.tracks = tracks;
	}
	public int getBaseTime() {
		return baseTime;
	}
	void setBaseTime(int baseTime) {
		this.baseTime = baseTime;
	}
}
