package ronak.apps.tdd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnItemClickEvent;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {

	TextView rev_tv;
	TextView tv;
	TextView random_tv;
	TextView flatMapTv;
	ObservableEditText et;
	
	Spinner lv;
	
	TextView combineLatestTv;
	
	String s = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		tv = (TextView) findViewById(R.id.tv);
		rev_tv = (TextView) findViewById(R.id.rev_tv);
		random_tv = (TextView) findViewById(R.id.random_text);
		et = (ObservableEditText) findViewById(R.id.et);
		flatMapTv = (TextView) findViewById(R.id.flat_map_text);
		combineLatestTv = (TextView) findViewById(R.id.combineLatestText);
		
		lv = (Spinner) findViewById(R.id.list_view);
		List<String> list = new ArrayList<String>();
		for (int i =0; i< 10; i++) {
			list.add("boogie");
		}
		
		lv.setAdapter(new CustomArrayAdapter(this, 0, 0, list));
		
		WidgetObservable.itemClicks(lv).map(new Func1<OnItemClickEvent, String>() {

			@Override
			public String call(OnItemClickEvent arg0) {
				Log.d("item","itemClicked");
				return "boogie";
			}
		}).subscribe(new Observer<String>() {

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNext(String arg0) {
				
			}
		});

//		AppUtils.bindViews(tv, et);
		
		/**
		 * Bind TextView such that the editText in CAPS
		 */
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
		
		/**
		 * Binds the text view such that it displays the editText after a delay
		 */
		
		et.getObservable().debounce(1, TimeUnit.SECONDS).
		map(new Func1<OnTextChangeEvent, String>() {

			@Override
			public String call(OnTextChangeEvent arg0) {
				return arg0.text().toString();
			}
			
		}).
		observeOn(AndroidSchedulers.mainThread()).subscribe(new TextViewObserver(tv));
		
		
		
		/**
		 * Binds text view to the number of messages the randomObservable
		 *  emitted in it's most recet burst
		 */
		randomObservable().debounce(1, TimeUnit.SECONDS)
				.map(new Func1<Integer, String>() {

					@Override
					public String call(Integer number) {
						return String.valueOf(number);
					}
				}).observeOn(AndroidSchedulers.mainThread()).
				subscribe(new TextViewObserver(random_tv));
		
		
		
		unitObservable().flatMap(new Func1<Integer, Observable<Integer>>() {

			@Override
			public Observable<Integer> call(Integer i) {
				return finitePeriodicObservable(3, 2000);
			}
		}).subscribeOn(Schedulers.newThread()).map(new Func1<Integer, String>() {

			@Override
			public String call(Integer i) {
				s = s + i;
				return s;
			}
		}).observeOn(AndroidSchedulers.mainThread()).subscribe(new TextViewObserver(flatMapTv));
		
		
	}
	
	/**
	 * Returns an observable that emits a random integer every 1 second
	 * @return Observable<Integer>
	 */
	
	public static Observable<Integer> randomObservable() {
		return Observable.create(new OnSubscribe<Integer>() {

			@Override
			public void call(Subscriber<? super Integer> s) {
			
				while (!s.isUnsubscribed()) {
					for (int i=0; i<Math.random() * 20; i++) {
						s.onNext(i); 
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
				
		}).subscribeOn(Schedulers.newThread()); 
			
	}
	
	/**
	 * Emits n integers one after another with a delay.
	 */
	
	public static Observable<Integer> finitePeriodicObservable(final int n, final int delay) {
		
		return Observable.create(new OnSubscribe<Integer>() {

			@Override
			public void call(Subscriber<? super Integer> s) {
				
				for (int i=0; i<n; i++) {
					s.onNext(i);
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				s.onCompleted();
				
			}
			
		}).subscribeOn(Schedulers.newThread());
		
	}
	
	public static Observable<Integer> unitObservable() {
		return Observable.create(new OnSubscribe<Integer>() {

			@Override
			public void call(Subscriber<? super Integer> s) {
				s.onNext(1);
				s.onCompleted();
			}
			
		});
	}
	
	/**
	 * Continuously emits the specified integer with specified gap.
	 * @param n
	 * @param delay
	 * @return
	 */
	public static Observable<Integer> simplePeriodicEmitter(final int n, final int delay) {
		return Observable.create(new OnSubscribe<Integer>() {

			@Override
			public void call(Subscriber<? super Integer> s) {
				while(!s.isUnsubscribed()) {
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					s.onNext(n);
				}
			}
			
		}).subscribeOn(Schedulers.newThread());
	}
	

	
}
