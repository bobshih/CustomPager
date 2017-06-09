package bob.mhmc.custompager.RecommendViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import bob.mhmc.custompager.R;

/**
 * Created by Bob on 2017/5/23.
 */

public class CustomPagerAdapter extends PagerAdapter {
	private List<Recommend> recommends = new ArrayList<>();
	private List<View> views = new ArrayList<>();

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public Object instantiateItem (ViewGroup container, int position)
	{
		View v = views.get (position);
		container.addView (v);
		return v;
	}

	@Override
	public void destroyItem (ViewGroup container, int position, Object object)
	{
		container.removeView (views.get (position));
	}

	@Override
	public int getItemPosition(Object object){
		return POSITION_NONE;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}

	public void addRecommend(Recommend recommend, View view){
		((TextView)view.findViewById(R.id.textView_title)).setText(recommend.getTitle());
		((TextView)view.findViewById(R.id.textView_type)).setText(recommend.getType());
		((TextView)view.findViewById(R.id.textView_address)).setText(recommend.getAddress());
		((TextView)view.findViewById(R.id.textView_phone)).setText(recommend.getPhone());
		((TextView)view.findViewById(R.id.textView_recommend)).setText(recommend.getRecommendContent());
		new DownloadImageTask((ImageView)view.findViewById(R.id.imageView_pic), this).execute(recommend.getPic());
		((ImageView)view.findViewById(R.id.imageView_pic)).setScaleType(ImageView.ScaleType.FIT_XY);
		recommends.add(recommend);
		addView(view);
	}

	public int addView (View v)
	{
		return addView (v, views.size());
	}

	public int addView (View v, int position)
	{
		views.add (position, v);
		return position;
	}

	public int removeView (ViewPager pager, View v)
	{
		return removeView (pager, views.indexOf (v));
	}

	public int removeView (ViewPager pager, int position)
	{
		// ViewPager doesn't have a delete method; the closest is to set the adapter
		// again.  When doing so, it deletes all its views.  Then we can delete the view
		// from from the adapter and finally set the adapter to the pager again.  Note
		// that we set the adapter to null before removing the view from "views" - that's
		// because while ViewPager deletes all its views, it will call destroyItem which
		// will in turn cause a null pointer ref.
		pager.setAdapter (null);
		views.remove (position);
		pager.setAdapter (this);

		return position;
	}

	public View getView (int position)
	{
		return views.get (position);
	}

	class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView imageView;
		CustomPagerAdapter adapter;

		public DownloadImageTask(ImageView image, CustomPagerAdapter adapter){
			imageView = image;
			this.adapter = adapter;
		}

		@Override
		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result){
			imageView.setImageBitmap(result);
			adapter.notifyDataSetChanged();
		}
	}
}
