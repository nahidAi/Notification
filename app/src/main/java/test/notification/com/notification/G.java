package test.notification.com.notification;


import android.app.Application;
import android.graphics.Typeface;

public class G extends Application{
    private  static Typeface font;
    @Override
    public void onCreate() {
        super.onCreate();
        font = Typeface.createFromAsset(getAssets(),"irsans_font.ttf");

    }
    public  Typeface getFont(){
        return  font;
    }
}
