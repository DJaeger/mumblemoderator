/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mumblemoderator.service.model;

/**
 *
 * @author frank
 */
interface Parcelable {

    public static class Creator<T> {

        public Creator() {
        
        }

        public Parcelable createFromParcel(final Parcel source) {
            return null;
        };

        public Parcelable[] newArray(final int size) {
            return null;
        };

        public Parcelable writeParcelable(Parcelable parcelable) {
            throw new UnsupportedOperationException("Not yet implemented");
        };

        public Parcelable writeParcelable(Parcelable parcelable, int counter) {
            throw new UnsupportedOperationException("Not yet implemented");
        };

        public Parcelable readParcelable(Parcelable parcelable) {
            throw new UnsupportedOperationException("Not yet implemented");
        };

    }

    public int describeContents();
    
    
    public void writeToParcel(final Parcel dest, final int flags);


}
