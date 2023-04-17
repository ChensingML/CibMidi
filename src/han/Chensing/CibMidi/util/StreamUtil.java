package han.Chensing.CibMidi.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class StreamUtil {

	private static final ByteBuffer shortBuffer;
	private static final ByteBuffer intBuffer;
	private static final ByteBuffer longBuffer;
	
	static {
		shortBuffer=ByteBuffer.allocate(Short.BYTES);
		intBuffer=ByteBuffer.allocate(Integer.BYTES);
		longBuffer=ByteBuffer.allocate(Long.BYTES);
	}
	
	public static byte readInt8(InputStream inputStream)
		throws IOException{
		return (byte)inputStream.read();
	}
	
	public static short readInt16(InputStream inputStream)
		throws IOException{
		try {
			byte[] b=new byte[2];
			inputStream.read(b);
			shortBuffer.put(b);
			shortBuffer.flip();
			return shortBuffer.getShort();
		}finally {
			shortBuffer.clear();
		}
	}
	
	public static int readInt32(InputStream inputStream)
			throws IOException{
		try {
			byte[] b=new byte[4];
			inputStream.read(b);
			intBuffer.put(b);
			intBuffer.flip();
			return intBuffer.getInt();
		}finally {
			intBuffer.clear();
		}
	}
	
	public static long readInt64(InputStream inputStream)
			throws IOException{
		try {
			byte[] b=new byte[8];
			inputStream.read(b);
			longBuffer.put(b);
			longBuffer.flip();
			return longBuffer.getLong();
		}finally {
			longBuffer.clear();
		}
	}
}
