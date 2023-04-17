package han.Chensing.CibMidi;

import han.Chensing.CibMidi.raw.MidiEvent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MidiTrack {
    private int num;
    private final List<MidiEvent> events;
    public MidiTrack(){
        this.events=new ArrayList<>();
    }

    public List<MidiEvent> getEvents() {
        return events;
    }
    public int getNum() {
        return num;
    }
    void setNum(int num) {
        this.num = num;
    }
}
