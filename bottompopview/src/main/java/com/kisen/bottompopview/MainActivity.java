package com.kisen.bottompopview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kisen.bottompopview.popview.BouncingMenu;

public class MainActivity extends AppCompatActivity {

    private View mPopupView;
    private BouncingMenu bouncingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPopupView = getLayoutInflater().inflate(R.layout.popup_view, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_more) {
            if (bouncingMenu == null) {
                bouncingMenu = BouncingMenu.makeMenu(this, mPopupView).show();
            } else {
                bouncingMenu.dismiss();
                bouncingMenu = null;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
