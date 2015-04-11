package ronak.apps.tdd;

import android.widget.TextView;

import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.android.widget.OnTextChangeEvent;
import rx.functions.Func1;

public class AppUtils implements OnSubscribe<String>{

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

	@Override
	public void call(Subscriber<? super String> arg0) {
		// TODO Auto-generated method stub
		arg0.onNext("response");
	}
	
	
	

}
