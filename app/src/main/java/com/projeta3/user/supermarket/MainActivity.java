package com.projeta3.user.supermarket;



import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

// Test mise a jour github
public class MainActivity extends AppCompatActivity {

    private Button scan_btn;
    private DatabaseReference mDatabase;
    private String text_scanned;
    private TextView textView;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    final Activity activity = this;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan_btn = (Button) findViewById(R.id.scan_button);

        textView = findViewById(R.id.texte_decode);

        final Auchan auchan= new Auchan();
        auchan.produit = "azerty";
        auchan.id = 0;
        auchan.rayon = "aze";
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ProductSectionGetter(auchan).execute();

                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_REQUEST_CODE);
                } else {
                    scanQrCode(activity);
                }*/
                //Test test = new Test("glagla", "surgelé");
                //mDatabase.child("Test").child("coucou").setValue(test);
            }
        });
    }


    private void scanQrCode(Activity activity)
    {
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();

                Intent Liste_Plan_Activity = new Intent(MainActivity.this, Liste_Plan_Activity.class);
                startActivity(Liste_Plan_Activity);

                text_scanned = result.getContents();
                textView.setText(text_scanned);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE)
        {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                scanQrCode(activity);
            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }}//end onRequestPermissionsResult
    private class Test {

        public String produit;
        public String rayon;

        public Test() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Test(String produit, String rayon) {
            this.produit = produit;
            this.rayon = rayon;
        }

    }
}

