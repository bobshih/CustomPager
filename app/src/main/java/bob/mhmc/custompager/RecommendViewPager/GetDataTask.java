package bob.mhmc.custompager.RecommendViewPager;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bob on 2017/5/23.
 */

public class GetDataTask extends AsyncTask<Void, Void, Void> {
	private String URL = "the url";
	private JSONArray objects;
	private AsyncResponse delegate;

	public GetDataTask(AsyncResponse delegate){
		this.delegate = delegate;
	}

	@Override
	protected Void doInBackground(Void... params) {
		HttpURLConnection connection;
		try {
			// open a URL connection to the Servlet
			java.net.URL url = new URL(URL);

			// Open a HTTP connection to the URL
			connection = (HttpURLConnection) url.openConnection();

			// Allow Inputs
			connection.setDoInput(true);

			// Don't use a cached copy.
			connection.setUseCaches(false);

			// set parameters

			// Use a post method.
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("charset", "utf-8");

			// decode response
			InputStream is = new BufferedInputStream(connection.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String inputLine;
			StringBuilder stringBuilder = new StringBuilder();
			while ((inputLine = br.readLine()) != null) {
				stringBuilder.append(inputLine);
			}
			objects = new JSONArray(stringBuilder.toString());			is.close();
		} catch (JSONException | IOException ex) {
			Log.e("Msg", "error: " + ex.getMessage(), ex);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result){
		List<Recommend> recommends = new ArrayList<>();
		for(int i = 0;i < objects.length();i++){
			Recommend re = new Recommend();
			try {
				JSONObject o = objects.getJSONObject(i);
				re.setAddress(o.getString("address"));
				re.setPhone(o.getString("phone"));
				re.setPic(o.getString("picture"));
				re.setRecommendContent(o.getString("recommendContent"));
				re.setTitle(o.getString("title"));
				re.setType(o.getString("type"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			recommends.add(re);
		}
		delegate.processFinished(recommends);
	}
}