package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.adminapp.HelpClasses.EventHelperClass;
import com.example.adminapp.HelpClasses.PlaceHelperClass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AddEvent extends AppCompatActivity implements LocationListener {

    ImageView main, image;
    TextInputLayout EventName, EventDesc;
    CheckBox[] cb = new CheckBox[5];
    int comingfrom;
    StringBuilder sb = new StringBuilder();
    Button next;
    Uri imageuri;
    String name, desc, userid, name1, desc1, date, time;
    StorageReference storageReference;
    TextInputEditText editplaceN, editplaceD;
    ProgressBar progressBar;
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    double lat1, lang1, latitude, longitude;
    private DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference("Events");
    LocationManager mLocationManager;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        btnDatePicker = findViewById(R.id.btn_date);
        cb[0] = findViewById(R.id.checkbox1);
        cb[1] = findViewById(R.id.checkbox2);
        cb[2] = findViewById(R.id.checkbox3);
        cb[3] = findViewById(R.id.checkbox4);
        cb[4] = findViewById(R.id.checkbox5);
        btnTimePicker = findViewById(R.id.btn_time);
        final String time2;
        txtDate = findViewById(R.id.in_date);
        txtTime = findViewById(R.id.in_time);
        main = findViewById(R.id.cancelToMain);
        next = findViewById(R.id.AddPlaceDatabase);
        progressBar = findViewById(R.id.progressBaradd);
        EventName = findViewById(R.id.place_name);
        EventDesc = findViewById(R.id.place_desc);
        lat1 = getIntent().getDoubleExtra("latitude", 0);
        lang1 = getIntent().getDoubleExtra("longitude", 0);
        editplaceN = findViewById(R.id.editplacename);
        editplaceD = findViewById(R.id.editplacedesc);
        image = findViewById(R.id.imagegallery);
        storageReference = FirebaseStorage.getInstance().getReference();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        name = getIntent().getStringExtra("nameP");
        desc = getIntent().getStringExtra("DescriptionP");
        comingfrom = getIntent().getIntExtra("coming", 0);
        if (comingfrom == 1) {
            if (getIntent().getBooleanExtra("cb0", true)) {
                cb[0].setChecked(true);
                sb.append("Food Stalls"+",");
            }
            if (getIntent().getBooleanExtra("cb1", true)) {
                cb[1].setChecked(true);
                sb.append("Concert"+",");
            }
            if (getIntent().getBooleanExtra("cb2", true)) {
                cb[2].setChecked(true);
                sb.append("Political Event"+",");
            }
            if (getIntent().getBooleanExtra("cb3", true)) {
                cb[3].setChecked(true);
                sb.append("Holiday Event"+",");
            }
            if (getIntent().getBooleanExtra("cb4", true)) {
                cb[4].setChecked(true);
                sb.append("Rides"+",");
            }
        }
        editplaceN.setText(name);
        editplaceD.setText(desc);

        btnDatePicker.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            String time = dayOfMonth + "-" + (monthOfYear+1) + "-" + year;
                            txtDate.setText(time);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        btnTimePicker.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR);
            mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this,
                    (view, hourOfDay, minute) -> {
                        String AM_PM;
                        if (hourOfDay < 12) {
                            AM_PM = "AM";
                            txtTime.setText(hourOfDay + ":" + minute + " " + AM_PM);
                        } else {
                            AM_PM = "PM";
                            txtTime.setText((hourOfDay - 12) + ":" + minute + " " + AM_PM);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        });


        //Current Location
        Criteria cri = new Criteria();
        String provider = mLocationManager.getBestProvider(cri, false);
        if (provider != null && !provider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location1 = mLocationManager.getLastKnownLocation(provider);
            mLocationManager.requestLocationUpdates(provider,2000,1,this);
            if(location1 != null){
                onLocationChanged(location1);
            }
            else {
                Toast.makeText(this, "location not found", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Provider null", Toast.LENGTH_SHORT).show();
        }
}

    public void GotoMain(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    public void GoToMap(View view) {
        name1 = EventName.getEditText().getText().toString().trim();
        desc1 = EventDesc.getEditText().getText().toString().trim();

        Intent intent = new Intent(getApplicationContext(), MapShow.class);
        intent.putExtra("latitude1",latitude);
        intent.putExtra("longitude1",longitude);
        intent.putExtra("nameP",name1);
        intent.putExtra("DescriptionP",desc1);
        intent.putExtra("cb0",cb[0].isChecked());
        intent.putExtra("cb1",cb[1].isChecked());
        intent.putExtra("cb2",cb[2].isChecked());
        intent.putExtra("cb3",cb[3].isChecked());
        intent.putExtra("cb4",cb[4].isChecked());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2 && data != null){
            imageuri = data.getData();
            image.setImageURI(imageuri);
        }
    }

    public void AddPicture(View view) {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,2);
    }

    public void GoPlaceData(View view) {
        if(imageuri != null){
            uploadImage(imageuri);
        }else {
            Toast.makeText(this, "Please include an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(Uri imageuri) {

        date = txtDate.getText().toString();
        time = txtTime.getText().toString();

        if(!CheckDate()){
            return;
        }
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//        StorageReference image = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageuri));
//        image.putFile(imageuri).addOnSuccessListener(taskSnapshot -> image.getDownloadUrl().addOnSuccessListener(uri -> {
//            System.out.println(uri.toString());
//            EventHelperClass AddNewEvent = new EventHelperClass(name,desc,uri.toString(),date,time,lat1,lang1,sb.toString());
//            rootNode.child(name).setValue(AddNewEvent);
//            Toast.makeText(AddEvent.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//        })).addOnProgressListener(snapshot -> {
//            progressBar.setVisibility(View.VISIBLE);
//            next.setVisibility(View.INVISIBLE);
//        }).addOnFailureListener(e -> {
//            progressBar.setVisibility(View.INVISIBLE);
//            next.setVisibility(View.VISIBLE);
//            Toast.makeText(AddEvent.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
//        });
    }


    private String getFileExtension(Uri MUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(MUri));
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    public boolean CheckDate(){
        date = txtDate.getText().toString();
        time = txtTime.getText().toString();
        Date date1=null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            date1 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();

        System.out.println(c.getTime().compareTo(date1));

        if(date.isEmpty() || time.isEmpty()){
            Toast.makeText(this, "Date or Time cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(c.getTime().compareTo(date1) == -1){
            Toast.makeText(this, "Date or Time is not Valid", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

}