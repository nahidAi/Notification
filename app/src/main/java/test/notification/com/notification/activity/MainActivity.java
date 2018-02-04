package test.notification.com.notification.activity;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import test.notification.com.notification.Quote;
import test.notification.com.notification.R;
import test.notification.com.notification.adapter.AdapterFragment;

public class MainActivity extends AppCompatActivity {
    public static Context context;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menuToolbar;
    private SQLiteDatabase database;
    private String desPath;
    FloatingActionButton floatingActionButton;
    public static final String DATABASE = "dbs_b_n.sqlite";

    public static ArrayList<Quote> person = new ArrayList<Quote>();
    public static ArrayList<Quote> favorite = new ArrayList<Quote>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity_main);
        context = getApplicationContext();
        setTabOption();
        setNavigationViewAndFloating();
        storagePermission();




    }

    private void selectfavorite() {
        database = SQLiteDatabase.openOrCreateDatabase(desPath + "dbs_b_n.sqlite", null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_b_n WHERE fav = 1", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String more = cursor.getString(cursor.getColumnIndex("more"));
            String imageAddress = cursor.getString(cursor.getColumnIndex("img_address"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));

            Quote quote = new Quote(name, content, more, imageAddress, id);
            quote.setName(name);
            quote.setContent(content);
            quote.setMore(more);
            quote.setImgAddress(imageAddress);
            quote.setId(id);
            favorite.add(quote);


        }
    }

    private void selectPerson() {
        database = SQLiteDatabase.openOrCreateDatabase(desPath + DATABASE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbl_b_n WHERE sobject = 'person'", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String more = cursor.getString(cursor.getColumnIndex("more"));
            String imageAddress = cursor.getString(cursor.getColumnIndex("img_address"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));

            Quote quote = new Quote(name, content, more, imageAddress, id);
            quote.setName(name);
            quote.setContent(content);
            quote.setMore(more);
            quote.setImgAddress(imageAddress);
            quote.setId(id);
            person.add(quote);

            Log.i("LOG", "person :" + person);


        }


    }

    private void setTabOption() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new AdapterFragment(getSupportFragmentManager()));

        TabLayout tabStrip = (TabLayout) findViewById(R.id.tabLayout);
        tabStrip.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();

        }

    }

    private void copyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
        outputStream.flush();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!favorite.isEmpty()) {
            favorite.clear();
           // selectfavorite();
        } else {
            person.clear();
           // selectPerson();
        }
    }

    public void storagePermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                checkAndOpenDatabase();

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    public void checkAndOpenDatabase() {
        try {
            desPath = "data/data/test.notification.com.notification/notification_database";
            Log.i("LOG", "des path : " + desPath);
            File file = new File(desPath+DATABASE);
            if (!file.exists()) {
                file.mkdirs();
                file.createNewFile();
                copyDB(getBaseContext().getAssets().open(DATABASE), new FileOutputStream(desPath + DATABASE));
            }
            selectPerson();
            selectfavorite();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void setNavigationViewAndFloating(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(floatingActionButton, " اسنک بار", Snackbar.LENGTH_LONG).show();
            }
        });
        menuToolbar = (ImageView) findViewById(R.id.menu_toolbar);
        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.setting) {
                    Toast.makeText(MainActivity.this, " کلیک شد", Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });

    }
}
