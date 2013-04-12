package mumblemoderator.service.model;

import java.util.logging.Level;
import mumblemoderator.Globals;
import mumblemoderator.util.Util;

public class User implements Parcelable {
	public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(final Parcel source) {
			return new User(source);
		};

		@Override
		public User[] newArray(final int size) {
			return new User[size];
		};

                @Override
                public User readParcelable(Parcelable parcelable) {
                    throw new UnsupportedOperationException("Not yet implemented");
                };

	};

	public static final int TALKINGSTATE_PASSIVE = 0;
	public static final int TALKINGSTATE_TALKING = 1;
	public static final int TALKINGSTATE_SHOUTING = 2;
	public static final int TALKINGSTATE_WHISPERING = 3;

	public int session;
	public String name;
	public String comment;
	public float averageAvailable;
	public int talkingState;
	public boolean isCurrent;
	
	private long talktimer;
	private int talkevents;
	private long talkstart;
	static private long overalltalktime = 0;
	static private int overalltalkevent = 1;
	

	public boolean muted;
	public boolean deafened;

	private Channel channel;

	public User() {
		initObject();
	}

	public User(final Parcel in) {
		initObject();
		readFromParcel(in);
	}

	private void initObject(){
		talktimer = 0L;
		talkevents = 1;
		talkstart = System.currentTimeMillis();

	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public final boolean equals(final Object o) {
		if (!(o instanceof User)) {
			return false;
		}
		return session == ((User) o).session;
	}

	public final Channel getChannel() {
		return this.channel;
	}

	@Override
	public final int hashCode() {
		return session;
	}

	public void sethashCode(int hashcode) {
		session = hashcode;
	}

	
	public void setChannel(final Channel newChannel) {
		// Moving user to another channel?
		// If so, remove the user from the original first.
		if (this.channel != null) {
			this.channel.userCount--;
		}

		// User should never leave channel without joining a new one?
//		Assert.assertNotNull(newChannel);
		if (newChannel == null) {
			Util.getLogger(User.class).log(Level.SEVERE, "User should never leave channel without joining a new one?");
			System.exit(2);
		}
			

		this.channel = newChannel;
		this.channel.userCount++;
	}

	@Override
	public final String toString() {
		return name;
	}

	public final String toextString() {
		return "User [session=" + session + ", name=" + name + ", channel=" +
			   channel + "]";
	}

	public void talkstart() {
		talkstart = System.currentTimeMillis();
		talkingState = TALKINGSTATE_TALKING;
	}
	
	public void talkstop() {
		long talkstop = System.currentTimeMillis();
		long talktime = talkstop - talkstart;
		
		Util.getLogger(User.class).log(Level.INFO, "\n" + "talktime:  "+ talktime + "\n" + "talkstop:  " + talkstop + "\n"  + "talkstart: " + talkstart);

		
		if (talktime > Globals.TALK_THRESHOLD) {
			talkevents++;
			talktimer = talktimer + talktime - Globals.TALK_THRESHOLD;

			overalltalkevent++;
			overalltalktime = overalltalktime + talktime - Globals.TALK_THRESHOLD;
			
		}
		talkingState = TALKINGSTATE_PASSIVE;
		
	}

	public long getStartTime() {
		return talkstart;
	}

	public long gettalktime(){
		if (talkingState != TALKINGSTATE_PASSIVE) {
			long talkstop = System.currentTimeMillis();
			long talktime = talkstop - talkstart;
			return talktimer + talktime;
		}
		return talktimer;

	}
	
	public long getavgtalktime(){
		return talktimer / talkevents;

	}

	public static long getoveralltalktime(){
		return overalltalktime;
		
	}

	public static long getoverallavgtalktime(){
		return overalltalktime / overalltalkevent;
		
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		dest.writeInt(0); // Version

		dest.writeInt(session);
		dest.writeString(name);
		dest.writeFloat(averageAvailable);
		dest.writeInt(talkingState);
		dest.writeBooleanArray(new boolean[] { isCurrent, muted, deafened });
		dest.writeParcelable(channel, 0);
	}

	private void readFromParcel(final Parcel in) {
		in.readInt(); // Version

		session = in.readInt();
		name = in.readString();
		averageAvailable = in.readFloat();
		talkingState = in.readInt();
		final boolean[] boolArr = new boolean[3];
		in.readBooleanArray(boolArr);
		isCurrent = boolArr[0];
		muted = boolArr[1];
		deafened = boolArr[2];
		channel = (Channel) in.readParcelable(null);
	}


}
