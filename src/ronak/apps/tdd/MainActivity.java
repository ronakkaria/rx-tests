package ronak.apps.tdd;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import rx.Observer;


public class MainActivity extends Activity {

	TextView rev_tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		TextView tv = (TextView) findViewById(R.id.tv);
		rev_tv = (TextView) findViewById(R.id.rev_tv);
		ObservableEditText et = (ObservableEditText) findViewById(R.id.et);

		AppUtils.bindViews(tv, et);
		
		
		AppUtils.bindViews(rev_tv, et, new Observer<String>() {
			
			@Override
			public void onNext(String text) {
				rev_tv.setText(text.toUpperCase());
			}
			
			@Override
			public void onError(Throwable arg0) {
				
			}
			
			@Override
			public void onCompleted() {
				
			}
		});
		
	}
	
}
