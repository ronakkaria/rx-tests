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
	
//	public static Observable<String> getDelayedObservable(Observable<OnTextChangeEvent> textObservable) {
//		
//		return getObservable().map(new Func1<OnTextChangeEvent, String>() {
//
//			@Override
//			public String call(OnTextChangeEvent arg0) {
//				return arg0.text().toString();
//			}
//		}).delay(1000, TimeUnit.MILLISECONDS).filter(new Func1<String, Boolean>() {
//
//			@Override
//			public Boolean call(String arg0) {
//				return arg0.equals(et.getText().toString());
//			}
//		}).observeOn(AndroidSchedulers.mainThread()).subscribe(new TextViewObserver(tv));
//		
//		
//	}
	
	
	

}
