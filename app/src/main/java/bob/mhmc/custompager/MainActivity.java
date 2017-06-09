package bob.mhmc.custompager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import bob.mhmc.custompager.RecommendViewPager.AsyncResponse;
import bob.mhmc.custompager.RecommendViewPager.CustomPagerAdapter;
import bob.mhmc.custompager.RecommendViewPager.GetDataTask;
import bob.mhmc.custompager.RecommendViewPager.Recommend;

public class MainActivity extends AppCompatActivity {
	// view component parameters
	private ViewPager viewPager;

	// app parameters
	private CustomPagerAdapter adapter = new CustomPagerAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager)findViewById(R.id.viewpager);
		viewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		new GetDataTask(new AsyncResponse() {
			@Override
			public void processFinished(List<Recommend> res) {
				for(Recommend recommend:res){
					View view = getLayoutInflater().inflate(R.layout.a_page, null);
					adapter.addRecommend(recommend, view);
				}
				viewPager.getAdapter().notifyDataSetChanged();
			}
		}).execute();
	}
}
