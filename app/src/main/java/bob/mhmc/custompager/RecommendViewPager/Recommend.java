package bob.mhmc.custompager.RecommendViewPager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bob on 2017/5/23.
 */

public class Recommend implements Parcelable {
	private String title;
	private String type;
	private String address;
	private String phone;
	private String recommendContent;
	private String pic;

	public Recommend(){
		title = "";
		type = "";
		address = "";
		phone = "";
		recommendContent = "";
		pic = "";
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getRecommendContent() {
		return recommendContent;
	}

	public String getPic() {
		return pic;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRecommendContent(String recommendContent) {
		this.recommendContent = recommendContent;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	// Parcelable Constructor
	public Recommend(Parcel in){
		title = in.readString();
		type = in.readString();
		address = in.readString();
		phone = in.readString();
		recommendContent = in.readString();
		pic = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(type);
		dest.writeString(address);
		dest.writeString(phone);
		dest.writeString(recommendContent);
		dest.writeString(pic);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Recommend> CREATOR = new Parcelable.Creator<Recommend>() {
		@Override
		public Recommend createFromParcel(Parcel in) {
			return new Recommend(in);
		}

		@Override
		public Recommend[] newArray(int size) {
			return new Recommend[size];
		}
	};
}
