package com.serdadu.adminserdadu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

public class UpdateOrderAct extends AppCompatActivity {

    DatabaseReference reference,masukindatauser,masukindataadmin;
    TextView id2,totalharga2,emailpemesan2,hppemesan2,totalhari2,tanggalpesan,namapulau2,tanggal2,status2;
    TextInputEditText namapemesan2,namapengirim;
    Button uploadbukti,pilihbukti;
    ImageView gambarbukti;


    Uri photo_location;
    Integer PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    Double tampilharga;
    String idpemesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);

        id2 = findViewById(R.id.id2);
        totalharga2 = findViewById(R.id.totalharga2);
        namapemesan2 = findViewById(R.id.namapemesan2);
        emailpemesan2 = findViewById(R.id.emailpemesan2);
        hppemesan2 = findViewById(R.id.hppemesan2);
        totalhari2 = findViewById(R.id.totalhari2);
        namapulau2 = findViewById(R.id.namapulau2);
        tanggal2 = findViewById(R.id.tanggal2);
        status2 = findViewById(R.id.status2);
        pilihbukti = findViewById(R.id.pilihbukti);
        gambarbukti = findViewById(R.id.gambarbukti);
        namapengirim = findViewById(R.id.namapengirim);
        tanggalpesan = findViewById(R.id.tanggalPesanan);
        namapengirim.clearFocus();


        String id_daridatabase_tiket = getIntent().getStringExtra("IDDB_tiket");
        String username_pemesan = getIntent().getStringExtra("IDDB_username");
        reference = FirebaseDatabase.getInstance().getReference().child("Order").child("orderadmin")
                .child(id_daridatabase_tiket);

        masukindatauser = FirebaseDatabase.getInstance().getReference().child("Order").child("orderuser").child(username_pemesan)
                .child(id_daridatabase_tiket);

        masukindataadmin = FirebaseDatabase.getInstance().getReference().child("Order").child("orderadmin").child(id_daridatabase_tiket);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Locale localeID = new Locale("ID", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        formatRupiah.setMaximumFractionDigits(0);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tampilharga = snapshot.child("totalharga").getValue(Double.class);
                id2.setText(snapshot.child("id").getValue().toString());
                totalharga2.setText(formatRupiah.format((double) tampilharga));
                namapemesan2.setText(snapshot.child("namapemesan").getValue().toString());
                emailpemesan2.setText(snapshot.child("emailpemesan").getValue().toString());
                tanggalpesan.setText("Tanggal Pemesanan : "+snapshot.child("tanggalbooking").getValue().toString());
                hppemesan2.setText(snapshot.child("hppemesan").getValue().toString());
                totalhari2.setText(snapshot.child("totalhari").getValue().toString());
                namapulau2.setText(snapshot.child("namapulau").getValue().toString());
                tanggal2.setText(snapshot.child("tanggal").getValue().toString());
                status2.setText(snapshot.child("status").getValue().toString());
                Picasso.get()
                        .load(snapshot.child("uri_gambar")
                                .getValue().toString()).centerCrop().fit()
                        .error(R.drawable.ic_launcher_foreground)
                        .fit().into(gambarbukti);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        if (!gambarbukti.equals(null)){
//            uploadbukti.setEnabled(false);
//            pilihbukti.setEnabled(false);
//        }

        /////////////////////////////////////////////////////////////////////////////////////////



        pilihbukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                KonfirmasiPembayaran();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                TolakPembayaran();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateOrderAct.this);
                builder.setMessage("Apakah Order Sudah Sesuai?").setPositiveButton("Terima Order", dialogClickListener)
                        .setNegativeButton("Tolak Order", dialogClickListener).show();
            }
        });

    }

    private void KonfirmasiPembayaran() {
        masukindataadmin.child("status").setValue("Sudah Dibayar");
        masukindatauser.child("status").setValue("Sudah Dibayar");

    }
    private void TolakPembayaran() {
        masukindataadmin.child("status").setValue("Pesanan Ditolak");
        masukindatauser.child("status").setValue("Pesanan Ditolak");

    }
}