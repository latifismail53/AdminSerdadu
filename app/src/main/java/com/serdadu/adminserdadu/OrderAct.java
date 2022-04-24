package com.serdadu.adminserdadu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.serdadu.adminserdadu.adapter.OrderAdapter;
import com.serdadu.adminserdadu.model.Order;

import java.util.ArrayList;

public class OrderAct extends AppCompatActivity {

    MaterialButton sudahdibayar,belumdibayar;
    RecyclerView recyclerView;
    CardView checkout;
    ArrayList<Order> list;
    OrderAdapter orderAdapter;
    DatabaseReference reference;
    TextView status;

    Query db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.recycler_order);
        sudahdibayar = findViewById(R.id.sudahdibayar);
        belumdibayar = findViewById(R.id.belumdibayar);
        checkout = findViewById(R.id.BayarBooking);
        status = findViewById(R.id.item_status);

        reference= FirebaseDatabase.getInstance().getReference().child("Order").child("orderadmin");
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager1 = new LinearLayoutManager(this){
        };
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager( manager1 );
        SnapHelper hilper = new LinearSnapHelper();
        hilper.attachToRecyclerView(recyclerView);
        list = new ArrayList<>();
        orderAdapter = new OrderAdapter(this,list);
        recyclerView.setAdapter(orderAdapter);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Order order = dataSnapshot.getValue(Order.class);
                    list.add(order);
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}