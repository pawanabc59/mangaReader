package com.example.mangareader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ms.square.android.expandabletextview.ExpandableTextView;

public class DeveloperProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_profile);

        ExpandableTextView akash_description = findViewById(R.id.akash_description);
        akash_description.setText("                              Akash Hadwale\n                           Backend Developer\n                                   BE-IT - 28\n              Don Bosco Institute of Technology\n                      A core backend developer");

        ExpandableTextView deepak_description = findViewById(R.id.deepak_description);
        deepak_description.setText("                             Deepak Paradkar\n                           Backend Developer\n                                   BE-IT - 57\n              Don Bosco Institute of Technology\n                      A core backend developer");

        ExpandableTextView pawan_description = findViewById(R.id.pawan_description);
        pawan_description.setText("                        Pawan Kumar Maurya\n                          FrontEnd Developer\n                                 BE-IT - 45\n              Don Bosco Institute of Technology\n                      A core frontend developer");


//        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view);

// IMPORTANT - call setText on the ExpandableTextView to set the text content to display
//        expTv1.setText(getString(R.string.developer));

    }
}
