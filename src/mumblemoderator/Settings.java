package mumblemoderator;

//import android.content.Context;
//import android.content.SharedPreferences;
//import android.media.AudioManager;
//import android.preference.PreferenceManager;

public class Settings {
	public static final String PREF_STREAM = "stream";
	public static final String ARRAY_STREAM_MUSIC = "music";
	public static final String ARRAY_STREAM_CALL = "call";

	public static final String PREF_JITTER = "buffering";
	public static final String ARRAY_JITTER_NONE = "none";
	public static final String ARRAY_JITTER_SPEEX = "speex";

	public static final String PREF_QUALITY = "quality";
	private static final String DEFAULT_QUALITY = "60000";

//	private final Object preferences;

	public Settings(final Object ctx) {
//		preferences = null;
	}

	public int getAudioQuality() {
            return Integer.parseInt(DEFAULT_QUALITY);
	}

	public int getAudioStream() {
            return 0;
        }

	public boolean isJitterBuffer() {
		return false;
	}
}
