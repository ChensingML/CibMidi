package han.Chensing.CibMidi;

import han.Chensing.CibMidi.raw.MidiEvent;

import java.util.List;

public class CibMidiMain {

	public static void main(String[] args) {
		MidiFile file=new MidiFile("th08_09.mid");
		file.analyze();
		MidiData data = file.getData();
		List<MidiTrack> tracks = data.getTracks();
		for (MidiTrack track:tracks){
			List<MidiEvent> events = track.getEvents();
			for(MidiEvent event:events){
				Object[] parameters = event.getParameters();
				System.out.println(
						event.getDeltaTime()+" "+
								event.getTrack()+" "+
								event.getEventType()+" "+
								(parameters==null?"":parameters.length));
			}
		}
	}

}
