package test.notification.com.notification;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import test.notification.com.notification.activity.MainActivity;

public class Main2Activity extends AppCompatActivity {
    public String name;
    public String content;
    public String more;
    public String imgAddress;
    public int id;
    private int layoutId;
    private String pageName;
    private TextView txtContent;
    private TextView txtMore;
    private ImageView avatar;
    private ImageView imgShare;
    private ImageView imgCopy;
    FloatingActionButton floatingActionButton;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private String desPath;
    private SQLiteDatabase database;
    public static final String DATABASE = "dbs_b_n.sqlite";
    private String favoriteState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            layoutId = Integer.parseInt(bundle.getString("id"));
            pageName = bundle.getString("name");
            if (pageName.equals("BigPerson")) {
                id = MainActivity.person.get(layoutId).getId();
                name = MainActivity.person.get(layoutId).getName();
                content = MainActivity.person.get(layoutId).getContent();
                more = MainActivity.person.get(layoutId).getMore();
                imgAddress = MainActivity.person.get(layoutId).getImgAddress();

            } else if (pageName.equals("favorite")) {
                id = MainActivity.favorite.get(layoutId).getId();
                name = MainActivity.favorite.get(layoutId).getName();
                content = MainActivity.favorite.get(layoutId).getContent();
                more = MainActivity.favorite.get(layoutId).getMore();
                imgAddress = MainActivity.favorite.get(layoutId).getImgAddress();

            }


        }
        Log.i("LOG", "id : " + id);
        Log.i("LOG", "id : " + name);
        Log.i("LOG", "id : " + content);
        Log.i("LOG", "id : " + more);
        Log.i("LOG", "id : " + imgAddress);

        txtContent = (TextView) findViewById(R.id.txtContent);
        txtMore = (TextView) findViewById(R.id.txtMore);
        avatar = (ImageView) findViewById(R.id.avatar);
        imgShare = (ImageView) findViewById(R.id.imgShare);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, content);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, name);
                startActivity(Intent.createChooser(shareIntent, "اشتراک"));

            }
        });
        imgCopy = (ImageView) findViewById(R.id.imgCopy);
        imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    final android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) Main2Activity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    final android.content.ClipData clipData = android.content.ClipData.newPlainText(more, content);
                    clipboardManager.setPrimaryClip(clipData);


                } else {
                    final android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) Main2Activity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setText(more);
                }
                Snackbar.make(v, " متن کپی شد", Snackbar.LENGTH_LONG).show();
            }
        });
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collAps);
        collapsingToolbarLayout.setTitle(name);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        txtContent.setText(content);
        txtMore.setText(more);
        int imageId = MainActivity.context.getResources().getIdentifier(imgAddress, "drawable", MainActivity.context.getPackageName());
        avatar.setImageResource(imageId);

        selectDB();
        if (selectFavoriteState()) {
            floatingActionButton.setImageResource(R.drawable.heart);
        } else {
            floatingActionButton.setImageResource(R.drawable.heart_outline);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectFavoriteState()) {
                    floatingActionButton.setImageResource(R.drawable.heart_outline);
                    updateUnFavorite();
                } else {
                    floatingActionButton.setImageResource(R.drawable.heart);
                    updateFavorite();
                }
            }
        });


    }

    private void selectDB() {
        desPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/book-database/";
        database = SQLiteDatabase.openOrCreateDatabase(desPath + DATABASE, null);
    }

    private boolean selectFavoriteState() {
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_b_n WHERE id = " + id, null);
        while (cursor.moveToNext()) {
            favoriteState = cursor.getString(cursor.getColumnIndex("fav"));
        }
        if (favoriteState.equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    private void updateFavorite() {
        database.execSQL("UPDATE tbl_b_n SET fav = 1 WHERE id=" + id);

    }

    private void updateUnFavorite() {
        database.execSQL("UPDATE tbl_b_n SET fav=0 WHERE id = " + id);

    }


}
