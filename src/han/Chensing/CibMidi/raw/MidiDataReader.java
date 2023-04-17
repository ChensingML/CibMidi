package han.Chensing.CibMidi.raw;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import han.Chensing.CibMidi.util.StreamUtil;
import han.Chensing.CibMidi.raw.MidiDataRaw.MidiFormat;

@SuppressWarnings("ResultOfMethodCallIgnored,unused")
public class MidiDataReader {

	public static final int MIDI_EVENT_MASK = 0xf0;
	public static final int MIDI_EVENT_TRACK_MASK = 0x0f;

	public static final int MIDI_EVENT_RELEASE = 0x80;//Key release
//XX YY
//XX	(00-7F)	Which note
//YY	(00-7F)	Force

	public static final int MIDI_EVENT_PRESS = 0x90;//Key press
//XX YY
//XX	(00-7F)	Which note
//YY	(00-7F)	Force

	public static final int MIDI_EVENT_KAT = 0xa0;//Key after touch
//XX YY
//XX	(00-7F)	Which note
//YY	(00-7F)	Force

	public static final int MIDI_EVENT_CTRLER = 0xb0;//Controller
//XX YY
//XX	(00-7F)	Controller code
//YY	(00-7F)	Controller parameters

	public static final int MIDI_EVENT_CGINST = 0xc0;//Change instrument
//XX
//XX	(00-7F)	Instrument code

	public static final int MIDI_EVENT_KATCH = 0xd0;//Channel of KAT
//XX
//XX	(00-7F)	Value

	public static final int MIDI_EVENT_SLIDE = 0xe0;//Slide
//XX YY
//XX	Pitch mod 128 (L)
//YY	Pitch div 128 (H)

	public static final int MIDI_EVENT_SYSCODE = 0xf0;//System code
//XX YY...
//XX	Size
//YY...	Content

	public static final int MIDI_EVENT_OTHER = 0xff;//Other format
//XX YY ZZ...
//XX	Type
//YY	Size
//ZZ...	Content
	public static final int MIDI_META_END = 0x2f;//End chunk


	public static MidiDataRaw fromFile(String file) {
		MidiDataRaw data = new MidiDataRaw();
		try {
			InputStream inputStream = new FileInputStream(file);
			MThdChunk mthdChunk = MThdChunk.getChunk(inputStream);
			data.setBaseTime(mthdChunk.unitTime);
			data.setTracks(mthdChunk.tracks);
			data.setFormat(MidiFormat.valueOfCode(mthdChunk.midiType));
			List<MidiChunk> chunks = data.getChunks();
			while (inputStream.available() > 0) {
				MTrkChunk rkChunk = MTrkChunk.getChunk(inputStream);
				MidiChunk chunk = new MidiChunk();
				chunk.setChunkHead(rkChunk);
				List<MidiEvent> events = chunk.getEvents();
				MidiEvent.EventType lastEventType=null;
				while (inputStream.available() > 0) {
					MidiEvent event=new MidiEvent();
					int deltaTime = readExpandByte(inputStream);
					int eventCommand = inputStream.read();
					event.setDeltaTime(deltaTime);
					if (eventCommand == MIDI_EVENT_OTHER) {
						event.setTrack(-1);
						event.setEventType(MidiEvent.EventType.Other);
						int code = inputStream.read();
						int length = readExpandByte(inputStream);
						inputStream.skip(length);
						if (code == MIDI_META_END)
							break;
						events.add(event);
						continue;
					}
					int eventTypeNum=eventCommand&MIDI_EVENT_MASK&0xFF;
					int trackNum=eventCommand&MIDI_EVENT_TRACK_MASK&0xFF;
					event.setTrack(trackNum);
					MidiEvent.EventType eventType = MidiEvent.EventType.valueOf(eventTypeNum);
					if (eventType==null) {
						if (lastEventType == null)
							continue;
						else
							eventType = lastEventType;
					}
					event.setEventType(eventType);
					switch (eventType){
						case Slide:
						case KeyAfterTouch:
						case Controller:
						case KeyUp:
						case KeyDown:{
							Object[] objects=new Object[]{
									StreamUtil.readInt8(inputStream),
									StreamUtil.readInt8(inputStream)
							};
							event.setParameters(objects);
							break;
						}
						case ChannelOfKeyAfterTouch:
						case ChangeInstrument:{
							Object[] objects=new Object[]{
									StreamUtil.readInt8(inputStream)
							};
							event.setParameters(objects);
							break;
						}
						case SystemCode:{
							int length = readExpandByte(inputStream);
							byte[] bytes=new byte[length];
							inputStream.read(bytes);
							Object[ ]objects=new Object[]{
									length,bytes
							};
							event.setParameters(objects);
							break;
						}
					}
					lastEventType=eventType;
					events.add(event);
				}
				chunks.add(chunk);
			}
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}

	public static int readExpandByte(InputStream inputStream)
			throws IOException {
		int out = 0;
		byte read;
		do {
			read = (byte) inputStream.read();
			out |= read & 0x7F;
			if ((read & 0x80) != 0x80)
				break;
			out <<= 7;
		} while (true);
		return out;
	}

	public static class MThdChunk {
		private final byte[] head;
		private short midiType;
		private short tracks;
		private short unitTime;

		public MThdChunk() {
			this.head = new byte[8];
		}

		public static MThdChunk getChunk(InputStream inputStream)
				throws IOException {
			MThdChunk chunk = new MThdChunk();
			inputStream.read(chunk.head);
			chunk.midiType = StreamUtil.readInt16(inputStream);
			chunk.tracks = StreamUtil.readInt16(inputStream);
			chunk.unitTime = StreamUtil.readInt16(inputStream);
			return chunk;
		}
	}

	public static class MTrkChunk {
		private final byte[] head;
		private int length;

		public MTrkChunk() {
			this.head = new byte[4];
		}

		public static MTrkChunk getChunk(InputStream inputStream)
				throws IOException {
			MTrkChunk chunk = new MTrkChunk();
			inputStream.read(chunk.head);
			chunk.length = StreamUtil.readInt32(inputStream);
			return chunk;
		}

		public int getLength() {
			return length;
		}
	}
}
