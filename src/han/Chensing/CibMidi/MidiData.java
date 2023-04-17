package han.Chensing.CibMidi;

import han.Chensing.CibMidi.raw.MidiChunk;
import han.Chensing.CibMidi.raw.MidiDataRaw;
import han.Chensing.CibMidi.raw.MidiEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MidiData {
    private final List<MidiTrack> tracks;
    MidiData(){
        this.tracks=new ArrayList<>();
    }

    public List<MidiTrack> getTracks() {
        return tracks;
    }

    public static MidiData formRawData(MidiDataRaw raw){
        MidiData midiData=new MidiData();
        List<MidiTrack> tracks = midiData.getTracks();
        int trackNum = getTrackNum(raw);
        for(int i=0;i<trackNum;i++){
            MidiTrack e = new MidiTrack();
            e.setNum(i);
            tracks.add(e);
        }
        List<MidiChunk> chunks = raw.getChunks();
        for(MidiChunk chunk:chunks){
            List<MidiEvent> events = chunk.getEvents();
            for(MidiEvent event:events){
                int track = event.getTrack();
                if (track<0)
                    continue;
                tracks.get(track).getEvents()
                        .add(event);
            }
        }
        return midiData;
    }

    private static int getTrackNum(MidiDataRaw raw){
        HashSet<Integer> hashSet=new HashSet<>();
        List<MidiChunk> chunks = raw.getChunks();
        for(MidiChunk chunk:chunks){
            List<MidiEvent> events = chunk.getEvents();
            for (MidiEvent event:events){
                int track = event.getTrack();
                hashSet.add(track);
            }
        }
        return hashSet.size();
    }
}
