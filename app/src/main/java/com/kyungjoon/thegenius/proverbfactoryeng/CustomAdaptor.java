package com.kyungjoon.thegenius.proverbfactoryeng;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdaptor extends ArrayAdapter<String> {
    ImageView imageView1;
    Context context;

    public CustomAdaptor(Context context, int resource) {

        super(context, 0, resource);
        this.context = context;  // initialize
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_one_row, parent, false);
        }

        // Get the data item for this position
        String strProverbOne = getItem(position);

        // Lookup view for data population
        TextView textViewProverbOne = (TextView) convertView.findViewById(R.id.tvOneProverb);

        imageView1 = (ImageView) convertView.findViewById(R.id.ivUserIcon);

        /*imageView1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), "msg msg", Toast.LENGTH_SHORT).show();
            }
        });*/

        setOnClick(imageView1, strProverbOne);

        // Populate the data into the template view using the data object
        textViewProverbOne.setText(strProverbOne);

        // Return the completed view to render on screen
        return convertView;
    }

    private void setOnClick(final ImageView btn, final String proverbOne){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gets a handle to the clipboard service.
                ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", proverbOne);
                clipboard.setPrimaryClip(clip);


                // Do whatever you want(str can be used here)
                Toast.makeText(context.getApplicationContext(),"\""+  proverbOne+ "\"" + " Copied.", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
