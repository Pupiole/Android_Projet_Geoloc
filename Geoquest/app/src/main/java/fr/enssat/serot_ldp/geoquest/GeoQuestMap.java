package fr.enssat.serot_ldp.geoquest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by yserot on 12/12/16.
 */

public class GeoQuestMap extends AppCompatActivity {
    private MapView map;
    private IMapController mapController;
    private ItemizedOverlay<OverlayItem> mMyLocationOverlay;
    private Balises[] Tab_Balises;
    private int hint_num = 0;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);

        Intent intent = getIntent();
        String nom_fichier = intent.getStringExtra("menu");

        map = new MapView(this);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(false);
        map.setMultiTouchControls(false);

        mapController = map.getController();
        mapController.setZoom(18);
        GeoPoint startPoint = new GeoPoint(48.7333, -3.4667);
        mapController.setCenter(startPoint);

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            LocationListener mlocListener = new GeoQuestMap.MyLocationListener();
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
        }

        final ArrayList<OverlayItem> items = new ArrayList<>();
        items.add(new OverlayItem("Geolocation", "My location", startPoint));
        this.mMyLocationOverlay = new ItemizedIconOverlay<>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, getApplicationContext());
        this.map.getOverlays().add(this.mMyLocationOverlay);


        // Hint

        ImageView imageHint = new ImageView(this);
        imageHint.setId(R.id.imageHintId);
        TextView textHint = new TextView(this);
        textHint.setId(R.id.textHintId);
        Button boutonHint = new Button(this);
        boutonHint.setText("Indice");

        final RelativeLayout relativeLayout = new RelativeLayout(this);
        final RelativeLayout hintLayout = new RelativeLayout(this);
        hintLayout.setId(R.id.hintLayout);
        hintLayout.setPadding(20, 20, 20, 20);
        hintLayout.setBackgroundColor(Color.rgb(255, 255, 255));
        final RelativeLayout.LayoutParams hintParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        final RelativeLayout.LayoutParams boutonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        boutonParams.addRule(RelativeLayout.BELOW, R.id.hintLayout);
        boutonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        final RelativeLayout.LayoutParams mapViewLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        final RelativeLayout.LayoutParams panelLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        final RelativeLayout.LayoutParams imageHintLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageHintLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        imageHintLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        final RelativeLayout.LayoutParams textHintLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textHintLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageHint.getId());
        textHintLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textHintLayoutParams.setMargins(15,0,0,0);

        hintLayout.addView(imageHint, imageHintLayoutParams);
        hintLayout.addView(textHint, textHintLayoutParams);
        relativeLayout.addView(map, mapViewLayoutParams);
        relativeLayout.addView(hintLayout, hintParams);
        relativeLayout.addView(boutonHint, boutonParams);
        setContentView(relativeLayout);

        boutonHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hintLayout.getVisibility() == View.VISIBLE)
                {
                    hintLayout.setVisibility(View.GONE);
                }
                else
                {
                    hintLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        if (nom_fichier.equals("Partie_test")) {
            try {
                JsonParser parser_Json = new JsonParser();
                Tab_Balises = parser_Json.getJson(this, R.raw.test);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            try {
                JsonParser parser_Json = new JsonParser();
                Tab_Balises = parser_Json.JsonParser(getApplicationContext().getFilesDir().toString() + File.separator + nom_fichier);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setHint(Tab_Balises[hint_num].getIndice().getTexte(), Tab_Balises[hint_num].getIndice().getImage());
    }

    private void setHint(String text, String urlImage) {
        Log.i("Map", "image : " + urlImage);
        TextView textHint = (TextView) findViewById(R.id.textHintId);
        ImageView imageView = (ImageView) findViewById(R.id.imageHintId);
        if (text == "") {
            textHint.setVisibility(View.GONE);
        } else {
            textHint.setVisibility(View.VISIBLE);
            textHint.setText(text);
        }
        if (urlImage == "") {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            new DownloadImageTask(imageView).execute(urlImage);
        }
    }

/* Class My Location Listener */

    private class MyLocationListener implements LocationListener {
        private final int distanceTampon = 5;

        @Override
        public void onLocationChanged(Location loc)
        {
            loc.getLatitude();
            loc.getLongitude();
            GeoPoint newPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());
            mapController.setCenter(newPoint);

            map.getOverlays().remove(mMyLocationOverlay);
            final ArrayList<OverlayItem> items = new ArrayList<>();
            items.add(new OverlayItem("Geolocation", "My location", newPoint));
            mMyLocationOverlay = new ItemizedIconOverlay<>(items,
                    new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                        @Override
                        public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                            return true;
                        }

                        @Override
                        public boolean onItemLongPress(final int index, final OverlayItem item) {
                            return false;
                        }
                    }, getApplicationContext());

            map.getOverlays().add(mMyLocationOverlay);


            if (distFrom(loc.getLatitude(), loc.getLongitude(),
                    Tab_Balises[hint_num].getCoordonnee().getLatitude(),
                    Tab_Balises[hint_num].getCoordonnee().getLongitude())
                    <= distanceTampon) {
                RelativeLayout hintLayout = (RelativeLayout) findViewById(R.id.hintLayout);
                hintLayout.setVisibility(View.VISIBLE);
                if (hint_num<Tab_Balises.length-1) {
                    hint_num++;
                    setHint(Tab_Balises[hint_num].getIndice().getTexte(), Tab_Balises[hint_num].getIndice().getImage());
                } else
                    setHint("Vous avez gagné !","http://t3.gstatic.com/images?q=tbn:ANd9GcTBKL45MTNFLNoSeqhQvER7XsB2LbK_Htl28rW7nt5GZWWTi8NxC5jQmuA");
            }
        }

        public double distFrom(double lat1, double lng1, double lat2, double lng2) {
            double earthRadius = 6371000; //meters
            double dLat = Math.toRadians(lat2-lat1);
            double dLng = Math.toRadians(lng2-lng1);
            double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                            Math.sin(dLng/2) * Math.sin(dLng/2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double dist = (float) (earthRadius * c);

            return dist;
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            bmImage.getLayoutParams().height = 300;
            bmImage.getLayoutParams().width = 300;
        }
    }

}
