package ronak.apps.tdd;

import android.util.Log;
import android.widget.TextView;

import rx.Observer;

public class TextViewObserver implements Observer<String> {
	
	TextView tv;

	public TextViewObserver(TextView tv) {
		this.tv = tv;
	}

	@Override
	public void onCompleted() {
		tv.setText(tv.getText().toString() + " Completed");
	}

	@Override
	public void onError(Throwable arg0) {
		Log.d("onError", "Error thrown" + arg0.getMessage());
	}

	@Override
	public void onNext(String text) {
		tv.setText(text);
	}

}
