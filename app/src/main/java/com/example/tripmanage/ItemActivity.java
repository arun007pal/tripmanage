package com.example.tripmanage;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripmanage.adapter.ItemAdapter;

import java.util.List;

public class ItemActivity extends AppCompatActivity {

    RecyclerView item_rec;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        item_rec=findViewById(R.id.item_rec);

        int s=getIntent().getIntExtra("pos",0);

        item_rec.setLayoutManager(new LinearLayoutManager(this));
        ItemAdapter itemAdapter=new ItemAdapter(this,GroupActivity.memberModel.getList());
        item_rec.setAdapter(itemAdapter);

    }
}