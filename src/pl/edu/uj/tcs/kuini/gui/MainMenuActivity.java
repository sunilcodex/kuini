package pl.edu.uj.tcs.kuini.gui;

import pl.edu.uj.tcs.kuini.KuiniActivity;
import android.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenuActivity extends ListActivity {

    private static final String[] MENU_ITEMS = {
        "Start new game",
        "Join existing game",
        "Settings",
        "About Kuini"
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, 
                R.layout.simple_list_item_1, 
                MENU_ITEMS));
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        switch (position) {
        case 0:
            startActivity(new Intent(this, KuiniActivity.class));
            break;
        default:
            Toast.makeText(this, "Not implemented yet :-(", Toast.LENGTH_SHORT).show();
        }
    
    }
    
}
