package com.kyungjoon.thegenius.proverbfactoryeng;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import static com.kyungjoon.thegenius.proverbfactoryeng.ProverbListAsyncTask.getDataFromHttpResponse;


public class HttpGetOneProverbAsyncTask extends AsyncTask<String, Void, String> {

    Context mContext;
    public View mView;
    public ProgressDialog progDialog;
    TextView rowTextView;
    LinearLayout myLinearLayout;
    LinearLayout parentLinearLayout;
    ImageView imageView1;

    public HttpGetOneProverbAsyncTask(Context pContext, TextView pTextView,  LinearLayout pParentLinearLayout ,LinearLayout innerLayout ,ImageView pImageView) {
        super();
        mContext = pContext;
        progDialog = new ProgressDialog(pContext);
        rowTextView= pTextView;
        myLinearLayout = innerLayout;
        parentLinearLayout = pParentLinearLayout;
        imageView1 = pImageView;
    }


    protected void onPreExecute() {
        this.progDialog.setMessage("Please wait... ^^");
        this.progDialog.show();
    }


    @Override
    protected String doInBackground(String... urls) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getDataFromHttpResponse(urls[0]);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String reqUrl) {

        try {
            JSONObject jsnobject = new JSONObject(reqUrl);
            String imageName = (String) jsnobject.get("imageName");
            String strProverb = (String) jsnobject.get("strProverbOne");


            //이미지뷰 크기 설정
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(950, 950);
            imageView1.setLayoutParams(layoutParams);


            // Picasso.with(getApplicationContext()).load("http://35.194.71.159:8080/image/"+imageName ).into(imageView1);
            Glide.with(mContext.getApplicationContext()).load("http://35.194.71.159:8080/image/"+imageName).centerCrop().crossFade().fitCenter().into(imageView1);
            /*Glide.with(context).load(path).centerCrop().crossFade().into(imageview);*/

            rowTextView.setTextSize(25);
            rowTextView.setText(strProverb);
            rowTextView.setGravity(Gravity.CENTER);
            parentLinearLayout.addView(imageView1,0);
            parentLinearLayout.addView(rowTextView, 1);

            /*parentLinearLayout.addView(myLinearLayout);*/

            /*this.myMethod(strProverb);*/


        } catch (Exception e) {

            e.printStackTrace();
        }finally {
            progDialog.dismiss();
        }

    }


}
