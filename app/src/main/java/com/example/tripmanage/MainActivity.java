package com.example.tripmanage;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.tripmanage.fragments.HomeFragment;
import com.example.tripmanage.fragments.MyAccountFragment;
import com.example.tripmanage.fragments.SettingFragment;
import com.example.tripmanage.model.GroupModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;




public class MainActivity extends AppCompatActivity {
    EditText editTextEmail;
    public static GroupModel groupModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
//        MongoClientURI mongoUri  = new MongoClientURI("mongodb+srv://palarunpal007:s7WubAE5apeVZVyo@cluster0.znvan1d.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
//        MongoClient mongoClient = new MongoClient(mongoUri);
//        MongoDatabase db = mongoClient.getDatabase("Cluster0");
//// Create a new collection in the database
//        db.createCollection("newCollection");
//
//        // List all collections in the database
//        for (String collectionName : db.listCollectionNames()) {
//            System.out.println(collectionName);
//        }
      /*  URL url="mongodb+srv://palarunpal007:<password>@cluster0.znvan1d.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0\n" +
                "\n"*/

//        MongoClient mongoClient = MongoClients.create();


    }
    BottomNavigationView.OnNavigationItemSelectedListener navListener=menuItem -> {
        Fragment selectedFragment = null;
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.myaccount) {
            selectedFragment = new MyAccountFragment();
        } else if (itemId == R.id.menu_setting) {
            selectedFragment = new SettingFragment();
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }
        return true;

    };



}