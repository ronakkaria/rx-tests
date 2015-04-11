package ronak.apps.tdd;

import android.widget.TextView;

import rx.Observer;
import rx.android.widget.OnTextChangeEvent;
import rx.functions.Func1;

public class AppUtils {

	public static void bindViews(TextView tv, ObservableEditText et) {
		TextViewObserver tObserver = new TextViewObserver(tv);

		et.getObservable().map(new Func1<OnTextChangeEvent, String>() {

			@Override
			public String call(OnTextChangeEvent arg0) {
				return arg0.text().toString();
			}

		}).subscribe(tObserver);
	}
	
	public static void bindViews(TextView tv, ObservableEditText et, Observer<String> ob) {
		
		et.getObservable().map(new Func1<OnTextChangeEvent, String>() {

			@Override
			public String call(OnTextChangeEvent arg0) {
				return arg0.text().toString();
			}

		}).subscribe(ob);
		
	}
	
	
	

}
