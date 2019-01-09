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

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.InputStream;

import eu.amirs.JSON;
import uz.shukurov.carrecognition.R;


public class ResultActivity extends Activity {

    private String extra[] = new String[2];
    private String result, plate, color, year, body_type, make_model, url_out, type, processingTime;
    private ImageView mImageView, mImageBodyType, mColorImageView;
    private TextView mType, mPlate, mColor, mModel, mBodyType, mYear, mProcessingTime;
    private ProgressDialog mProgressDialog;

    private JSON json;

    private int x1, x2, x3, x4, y1, y2, y3, y4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        toolbarMethod();

        jsonConverting();

        givingView();

        new DownloadImage().execute(url_out);


        mPlate.setText(plate);

        settingDataToElements();
    }

    private void settingDataToElements() {
        setColor();

        mYear.setText(year);

        setBodyType();

        String make_model_upperCase = make_model.substring(0, 1).toUpperCase() + make_model.substring(1);

        mModel.setText(make_model_upperCase);

        int separate = type.indexOf("_");
        String brand = type.substring(0, separate);
        String model = type.substring(separate + 1);
        String car_type = brand.substring(0, 1).toUpperCase() + brand.substring(1) + " " + model.substring(0, 1).toUpperCase() + model.substring(1);
        mType.setText(car_type);


        mProcessingTime.setText("Processing Time: " + processingTime);
    }

    private void givingView() {
        mImageView = findViewById(R.id.imageView);
        mType = findViewById(R.id.mType);
        mPlate = findViewById(R.id.mPlate);
        mColor = findViewById(R.id.mColor);
        mModel = findViewById(R.id.mModel);
        mYear = findViewById(R.id.mYear);
        mBodyType = findViewById(R.id.mBodyType);
        mProcessingTime = findViewById(R.id.processingTime);
        mImageBodyType = findViewById(R.id.iv_body);
        mColorImageView = findViewById(R.id.mColorImage);

    }

    private void jsonConverting() {

        extra = getIntent().getStringArrayExtra("EXTRA_SESSION_ID");

        result = extra[0];
        url_out = extra[1];
        json = new JSON(result);

        getJsonOutput();


    }

    private void toolbarMethod() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });
    }

    private void setColor() {

        String color_upperCase = color.substring(0, 1).toUpperCase() + color.substring(1);
        mColor.setText(color_upperCase);

        switch (color) {
            case ("white"):
                mColorImageView.setColorFilter(getResources().getColor(R.color.white));
                break;
            case ("black"):
                mColorImageView.setColorFilter(getResources().getColor(R.color.black));
                break;
            case ("blue"):
                mColorImageView.setColorFilter(getResources().getColor(R.color.blue));
                break;
            case ("brown"):
                mColorImageView.setColorFilter(getResources().getColor(R.color.brown));
                break;
            case ("gold-beige"):
                mColorImageView.setColorFilter(getResources().getColor(R.color.gold_beige));
                break;
            case ("green"):
                mColorImageView.setColorFilter(getResources().getColor(R.color.green));
                break;
            case ("orange"):
                mColorImageView.setColorFilter(getResources().getColor(R.color.orange));
                break;
            case ("pink"):
                mColorImageView.setColorFilter(getResources().getColor(R.color.pink));
                break;
            case ("purple"):
                mColorImageView.setColorFilter(getResources().getColor(R.color.purple));
                break;
            case ("red"):
                mColorImageView.setColorFilter(getResources().getColor(R.color.red));
                break;
            case ("silver-gray"):
                mColorImageView.setColorFilter(getResources().getColor(R.color.silver_gray));
                break;
            case ("yellow"):
                mColorImageView.setColorFilter(getResources().getColor(R.color.yellow));
                break;
            default:
                mColorImageView.setVisibility(View.INVISIBLE);

        }

    }

    private void setBodyType() {

        switch (body_type) {
            case "antique":
                mBodyType.setText(getString(R.string.antique));
                break;
            case "missing":
                mBodyType.setText(getString(R.string.missing));
                break;
            case "motorcycle":
                mBodyType.setText(getString(R.string.motorcycle));
                mImageBodyType.setVisibility(View.INVISIBLE);
                break;
            case "sedan-compact":
                mBodyType.setText(getString(R.string.sedan_compact));
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type6));
                break;
            case "sedan-convertible":
                mBodyType.setText(getString(R.string.sedan_convertible));
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type6));
                break;
            case "sedan-sports":
                mBodyType.setText(getString(R.string.sedan_sports));
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type5));
                break;
            case "sedan-standard":
                mBodyType.setText(getString(R.string.sedan_standard));
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type6));
                break;
            case "sedan-wagon":
                mBodyType.setText(getString(R.string.sedan_wagon));
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type1));
                break;
            case "suv-crossover":
                mBodyType.setText(getString(R.string.suv_crossover));
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type7));
                break;
            case "suv-standard":
                mBodyType.setText(getString(R.string.suv_standard));
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type7));
                break;
            case "suv-wagon":
                mBodyType.setText(getString(R.string.suv_wagon));
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type3));
                break;
            case "tractor-trailer":
                mBodyType.setText(getString(R.string.tractor_trailer));
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type4));
                break;
            case "truck-standard":
                mBodyType.setText(getString(R.string.tractor_standard));
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type8));
                break;
            case "van-full":
                mBodyType.setText(getString(R.string.van_full));
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type8));
                break;
            case "van-mini":
                mBodyType.setText(getString(R.string.van_mini));
                mImageBodyType.setImageDrawable(getResources().getDrawable(R.drawable.body_type2));
                break;
            default:
                mBodyType.setText(body_type);

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
