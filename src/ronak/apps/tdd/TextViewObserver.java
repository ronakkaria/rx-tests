package ronak.apps.tdd;

import android.widget.TextView;

import rx.Observer;

public class TextViewObserver implements Observer<String> {
	
	TextView tv;

	public TextViewObserver(TextView tv) {
		this.tv = tv;
	}

	@Override
	public void onCompleted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNext(String text) {
		tv.setText(text);
	}

}
