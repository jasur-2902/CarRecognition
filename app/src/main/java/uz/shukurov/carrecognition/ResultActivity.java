package uz.shukurov.carrecognition;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.InputStream;

import eu.amirs.JSON;
import uz.shukurov.carrecognition.R;


public class ResultActivity extends Activity {

    private String extra[] = new String[2];
    private String result, plate, color, year, body_type, make_model, url_out, type, processingTime;
    private ImageView mImageView, mImageBodyType;
    private TextView mType, mPlate, mColor, mModel, mBodyType, mYear, mProcessingTime;
    private ProgressDialog mProgressDialog;

    private JSON json;

    private int x1, x2, x3, x4, y1, y2, y3, y4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        extra = getIntent().getStringArrayExtra("EXTRA_SESSION_ID");

        result = extra[0];
        url_out = extra[1];


        json = new JSON(result);

        getJsonOutput();


        mImageView = findViewById(R.id.imageView);

        new DownloadImage().execute(url_out);


        mType = findViewById(R.id.mType);
        mPlate = findViewById(R.id.mPlate);
        mColor = findViewById(R.id.mColor);
        mModel = findViewById(R.id.mModel);
        mYear = findViewById(R.id.mYear);
        mBodyType = findViewById(R.id.mBodyType);
        mProcessingTime = findViewById(R.id.processingTime);
        mImageBodyType = findViewById(R.id.iv_body);


        mPlate.setText(plate);

        mColor.setText("Color: " + color);

        mYear.setText("Year: " + year);

        setBodyType();

        mModel.setText("Brand: " + make_model);

        mType.setText("Model: " + type);

        mProcessingTime.setText("Processing Time: " + processingTime);

    }

    private void setBodyType() {

        switch (body_type) {
            case "antique":
                mBodyType.setText("Body type: " + "Antique");
                break;
            case "missing":
                mBodyType.setText("Body type: Missing");
                break;
            case "motorcycle":
                mBodyType.setText("Body type: Motorcycle");
                mImageBodyType.setVisibility(View.INVISIBLE);
                break;
            case "sedan-compact":
                mBodyType.setText("Body type: Sedan Compact");
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type6));
                break;
            case "sedan-convertible":
                mBodyType.setText("Body type: Sedan Convertible");
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type6));
                break;
            case "sedan-sports":
                mBodyType.setText("Body type: Sedan Sports");
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type5));
                break;
            case "sedan-standard":
                mBodyType.setText("Body type: Sedan Standard");
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type6));
                break;
            case "sedan-wagon":
                mBodyType.setText("Body type: Sedan Wagon");
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type1));
                break;
            case "suv-crossover":
                mBodyType.setText("Body type: SUV Crossover");
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type7));
                break;
            case "suv-standard":
                mBodyType.setText("Body type: SUV Standard");
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type7));
                break;
            case "suv-wagon":
                mBodyType.setText("Body type: SUV Wagon");
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type3));
                break;
            case "tractor-trailer":
                mBodyType.setText("Body type: Tractor Trailer");
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type4));
                break;
            case "truck-standard":
                mBodyType.setText("Body type: Tractor Standard");
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type8));
                break;
            case "van-full":
                mBodyType.setText("Body type: VAN Full");
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type8));
                break;
            case "van-mini":
                mBodyType.setText("Body type: VAN Mini");
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type2));
                break;
            default:
                mBodyType.setText("Body type: " + body_type);

        }


    }

    private void getJsonOutput() {
        plate = json.key("results").index(0).key("plate").stringValue();
        color = json.key("results").index(0).key("vehicle").key("color").index(0).key("name").stringValue();
        make_model = json.key("results").index(0).key("vehicle").key("make").index(0).key("name").stringValue();
        body_type = json.key("results").index(0).key("vehicle").key("body_type").index(0).key("name").stringValue();
        year = json.key("results").index(0).key("vehicle").key("year").index(0).key("name").stringValue();
        type = json.key("results").index(0).key("vehicle").key("make_model").index(0).key("name").stringValue();

        processingTime = json.key("processing_time").key("plates").toString();

        x1 = Integer.valueOf(json.key("results").index(0).key("coordinates").index(0).key("x").stringValue());
        y1 = Integer.valueOf(json.key("results").index(0).key("coordinates").index(0).key("y").stringValue());

        x2 = Integer.valueOf(json.key("results").index(0).key("coordinates").index(1).key("x").stringValue());
        y2 = Integer.valueOf(json.key("results").index(0).key("coordinates").index(1).key("y").stringValue());


        x3 = Integer.valueOf(json.key("results").index(0).key("coordinates").index(2).key("x").stringValue());
        y3 = Integer.valueOf(json.key("results").index(0).key("coordinates").index(2).key("y").stringValue());

        x4 = Integer.valueOf(json.key("results").index(0).key("coordinates").index(3).key("x").stringValue());
        y4 = Integer.valueOf(json.key("results").index(0).key("coordinates").index(3).key("y").stringValue());
    }


    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ResultActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Download Image Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap decodedByte) {


            Paint paint = new Paint();
            paint.setColor(Color.RED);

            paint.setStrokeWidth(10);


            //Drawing line around plate
            Bitmap tempBitmap = Bitmap.createBitmap(decodedByte.getWidth(), decodedByte.getHeight(), Bitmap.Config.RGB_565);
            Canvas tempCanvas = new Canvas(tempBitmap);
            tempCanvas.drawBitmap(decodedByte, 0, 0, null);


            tempCanvas.drawLine(x1, y1, x2, y2, paint);
            tempCanvas.drawLine(x1, y1, x4, y4, paint);
            tempCanvas.drawLine(x3, y3, x4, y4, paint);
            tempCanvas.drawLine(x3, y3, x2, y2, paint);


            mImageView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));

            // Close progressdialog
            mProgressDialog.dismiss();
        }
    }

}
