package mumblemoderator.service.model;

import java.util.ArrayList;
import java.util.List;


public class Channel implements Parcelable {
	public static final Parcelable.Creator<ChannelInt> CREATOR = new Creator<ChannelInt>() {
		@Override
		public Channel createFromParcel(final Parcel source) {
			return new Channel(source);
		}

		@Override
		public Channel[] newArray(final int size) {
			return new Channel[size];
		}
                
                @Override
                public Channel readParcelable(Parcelable parcelable) {
                    throw new UnsupportedOperationException("Not yet implemented");
                };

	};

	public int id;
	public int pid;
	public String name;
	public int userCount;

	/**
	 * Value signaling whether this channel has just been removed.
	 * Once this value is set the connection signals one last update for the
	 * channel which should result in the channel being removed from all the
	 * caches where it might be stored.
	 */
	public boolean removed = false;
	public List<Integer> links;

	public Channel() {
		this.links = new ArrayList<Integer>();

	}

	public Channel(String name) {
		this();
		this.name = name;

	}

	public Channel(final Parcel parcel) {
		this();
		readFromParcel(parcel);
	
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public final boolean equals(final Object o) {
		if (!(o instanceof Channel)) {
			return false;
		}
		return id == ((Channel) o).id;
	}

	@Override
	public final int hashCode() {
		return id;
	}

	public final String toextString() {
		return "Channel [id=" + id + ", name=" + name + ", userCount=" +
			   userCount + "]";
	}

	@Override
	public final String toString() {
		return name;
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		dest.writeInt(0); // Version

		dest.writeInt(id);
		dest.writeString(name);
		dest.writeInt(userCount);
	}

	private void readFromParcel(final Parcel in) {
		in.readInt(); // Version

		id = in.readInt();
		name = in.readString();
		userCount = in.readInt();
	}
}
