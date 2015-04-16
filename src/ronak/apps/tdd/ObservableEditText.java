package ronak.apps.tdd;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import rx.Observable;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;

public class ObservableEditText extends EditText {

	private Observable<OnTextChangeEvent> mObservable;
	
	
	public ObservableEditText(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ObservableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public ObservableEditText(Context context) {
		super(context);
	}
	
	public Observable<OnTextChangeEvent> getObservable() {
		if (mObservable == null)
			mObservable = WidgetObservable.text(this);
		return mObservable;
	}
	
	
	
}
