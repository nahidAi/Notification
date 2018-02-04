package test.notification.com.notification;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class CustomFontTextview extends TextView{
    public CustomFontTextview(Context context) {
        super(context);
        if (!isInEditMode()){
            setUpTextView();
        }
    }

    public CustomFontTextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()){
            setUpTextView();
        }
    }

    public CustomFontTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()){
            setUpTextView();
        }
    }

    public CustomFontTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode()){
            setUpTextView();
        }
    }

    private  void setUpTextView(){
        G g = (G) getContext().getApplicationContext();
        setTypeface(g.getFont());


    }
}
