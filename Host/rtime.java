import java.io.IOException;
import java.util.GregorianCalendar;

public class rtime {
	private static enum Format {
		DECIMAL,
		HEX,
		BINARY,
		COMMAND;
	}
	
	private static String h8(long v) {
		String h = "00000000" + Long.toHexString(v).toUpperCase();
		return h.substring(h.length() - 8);
	}
	
	public static void main(String[] args) throws IOException {
		Format format = Format.DECIMAL;
		int argi = 0;
		while (argi < args.length) {
			String arg = args[argi++];
			if (arg.startsWith("-") && arg.length() >= 2) {
				switch (arg.charAt(1)) {
				case 'd':
					format = Format.DECIMAL;
					break;
				case 'h':
					format = Format.HEX;
					break;
				case 'b':
					format = Format.BINARY;
					break;
				case 'r':
					format = Format.COMMAND;
					break;
				default:
					System.out.println("Usage: java rtime [-d|-h|-b|-r]");
					return;
				}
			} else {
				System.out.println("Usage: java rtime [-d|-h|-b|-r]");
				return;
			}
		}
		
		GregorianCalendar c = new GregorianCalendar();
		long days = c.getTimeInMillis() / 86400000;
		long msec = c.getTimeInMillis() % 86400000;
		long tzn = c.get(GregorianCalendar.ZONE_OFFSET);
		long dst = c.get(GregorianCalendar.DST_OFFSET);
		
		switch (format) {
		case DECIMAL:
			System.out.println(days);
			System.out.println(msec);
			System.out.println(tzn);
			System.out.println(dst);
			break;
		case HEX:
			System.out.println(h8(days));
			System.out.println(h8(msec));
			System.out.println(h8(tzn));
			System.out.println(h8(dst));
			break;
		case BINARY:
			System.out.write(new byte[] {
				(byte)(days >> 24L), (byte)(days >> 16L), (byte)(days >> 8L), (byte)(days),
				(byte)(msec >> 24L), (byte)(msec >> 16L), (byte)(msec >> 8L), (byte)(msec),
				(byte)(tzn >> 24L), (byte)(tzn >> 16L), (byte)(tzn >> 8L), (byte)(tzn),
				(byte)(dst >> 24L), (byte)(dst >> 16L), (byte)(dst >> 8L), (byte)(dst),
			});
			System.out.flush();
			break;
		case COMMAND:
			System.out.write(new byte[] {
				'r', 0x11, 0, 0,
				(byte)(days >> 24L), (byte)(days >> 16L), (byte)(days >> 8L), (byte)(days),
				'r', 0x13, 0, 0,
				(byte)(msec >> 24L), (byte)(msec >> 16L), (byte)(msec >> 8L), (byte)(msec),
				'r', 0x14, 0, 0,
				(byte)(tzn >> 24L), (byte)(tzn >> 16L), (byte)(tzn >> 8L), (byte)(tzn),
				'r', 0x15, 0, 0,
				(byte)(dst >> 24L), (byte)(dst >> 16L), (byte)(dst >> 8L), (byte)(dst),
			});
			System.out.flush();
			break;
		}
	}
}
