package han.Chensing.CibMidi;

import han.Chensing.CibMidi.raw.MidiDataRaw;
import han.Chensing.CibMidi.raw.MidiDataReader;

import java.io.File;

@SuppressWarnings("unused")
public class MidiFile {
	private final File file;
	private MidiDataRaw dataRaw;
	private MidiData data;
	public MidiFile(String filePath) {
		this(new File(filePath));
	}
	public MidiFile(File file) {
		this.file=file;
	}
	public void analyze() {
		analyzeRaw();
		this.data=MidiData.formRawData(dataRaw);
	}
	public void analyzeRaw(){
		this.dataRaw = MidiDataReader.fromFile(file.getAbsolutePath());
	}
	public MidiDataRaw getDataRaw() {
		return dataRaw;
	}
	public MidiData getData() {
		return data;
	}
}
