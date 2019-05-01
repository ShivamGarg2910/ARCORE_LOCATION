package com.example.setlocalposition;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity<client> extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    List<Result> sortingList = new ArrayList<>();

    double lat, lon;
    String URL;
    //Object[] arr;
    FusedLocationProviderClient client;


    ArFragment arFragment;
    ModelRenderable lampPostRenderable;
    ViewRenderable txtbox1,txtbox2,txtbox3,txtbox4,txtbox5,txtbox6,txtbox7,txtbox8,txtbox9,txtbox10;


    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.activity_main);

        lat = 12.8233558;
        lon = 80.0423585;
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        getData();
        ViewRenderable.builder()
                .setView(this,R.layout.textview1)
                .build()
                .thenAccept(renderable ->  txtbox1 =renderable);
        ViewRenderable.builder()
                .setView(this,R.layout.textview1)
                .build()
                .thenAccept(renderable ->  txtbox2 =renderable);
        ViewRenderable.builder()
                .setView(this,R.layout.textview1)
                .build()
                .thenAccept(renderable ->  txtbox3 =renderable);
        ViewRenderable.builder()
                .setView(this,R.layout.textview1)
                .build()
                .thenAccept(renderable ->  txtbox4 =renderable);
        ViewRenderable.builder()
                .setView(this,R.layout.textview1)
                .build()
                .thenAccept(renderable ->  txtbox5 =renderable);
        ViewRenderable.builder()
                .setView(this,R.layout.textview1)
                .build()
                .thenAccept(renderable ->  txtbox6 =renderable);
        ViewRenderable.builder()
                .setView(this,R.layout.textview1)
                .build()
                .thenAccept(renderable ->  txtbox7 =renderable);
        ViewRenderable.builder()
                .setView(this,R.layout.textview1)
                .build()
                .thenAccept(renderable ->  txtbox8 =renderable);
        ViewRenderable.builder()
                .setView(this,R.layout.textview1)
                .build()
                .thenAccept(renderable ->  txtbox9 =renderable);
        ViewRenderable.builder()
                .setView(this,R.layout.textview1)
                .build()
                .thenAccept(renderable ->  txtbox10 =renderable);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("Picture frame.sfb"))
                .build()
                .thenAccept(renderable -> lampPostRenderable = renderable)
                .exceptionally(throwable -> {
                    Toast toast =
                            Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return null;
                });

        arFragment.setOnTapArPlaneListener(
                (HitResult hitresult, Plane plane, MotionEvent motionevent) -> {

                    if (lampPostRenderable == null) {
                        return;
                    }
                    Anchor anchor = hitresult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    //anchorNode.setLocalPosition(Vector3.zero());
                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    double x;
                    double z;
                    // saari values bohot choti hain
                    if(sortingList.size()==0)
                    {
                        Toast toast = Toast.makeText(this, "no location", Toast.LENGTH_LONG);
                        toast.show();
                    }else{
                        Toast toast = Toast.makeText(this, sortingList.size()+"", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    double a = (double) sortingList.get(0).getDis();
                    Node nameview1=new Node();
                    a=-a;
                    a+=20;
                    nameview1.setParent(anchorNode);
                    nameview1.setLocalPosition(new Vector3( 0f,1f,(float)a));
                    nameview1.setRenderable(txtbox1);
                    TextView txt_nam1e=(TextView)txtbox1.getView();
                    txt_nam1e.setText(sortingList.get(0).getName());
                    Log.i("yo",sortingList.get(0).getName());

                    Log.i("akadi"," "+a);
                    if(sortingList.size()!=0) {
                        //txtbox2 = new ViewRenderable[3];
                        /*for (int i = 0; i < 4; i++){
                            int finalI = i;
                            ViewRenderable.builder()
                                    .setView(this,R.layout.textview1)
                                    .build()
                                    .thenAccept(renderable ->  txtbox2[finalI] =renderable);
                            Log.i("yo",txtbox2[finalI]+"");
                        }*/
                        int x1;
                        if(sortingList.size()>10)
                            x1 = 10;
                        else
                            x1 = sortingList.size();
                        for (int i = 1; i < x1; i++) {  // looping 1 se start kari hai kyonki 0 toh already apan tap karenge isliye nahi chal rahe tha
                            Log.i("yo","qweqw");
                            //ViewRenderable v=txtbox2[i];

                            double B = getAngle(sortingList.get(0), sortingList.get(i), 'b');
                            //B = Math.toRadians(B); //baar baar radians mein kyon change kar raha hai
                            Log.i("getRad","again "+B);
                            double C = getAngle(sortingList.get(0), sortingList.get(i), 'c');
                            //C = Math.toRadians(C);
                            double c = 0;
                            c = getDistance((double) sortingList.get(0).getLat(), (double) sortingList.get(i).getLat(), (double) sortingList.get(0).getLon(),
                                    (double) sortingList.get(i).getLon());
                            double b = (double) sortingList.get(i).getDis();
                            if (C < 90) {
                                x = c * Math.sin(B);
                                z = c * Math.cos(B);
                            } else if (C > 90) {
                                x = c * Math.cos(B);
                                z = c * Math.sin(B);
                            } else {
                                x = b;
                                z = a;
                            }

                            if ((double) sortingList.get(0).getLat() > lat) {
                                if ((double) sortingList.get(i).getLon() < lon)
                                    x = -x;
                            } else {
                                if ((double) sortingList.get(i).getLon() > lon)
                                    x = -x;
                            }
                            z+=a;
                            //x /= 10;
                            z /= 100;
                            Log.i("distttt",x+"  x was thsat z is this   "+z);
                            Node nameview=new Node();

                            TextView txt_name = null;
                            switch (i){
                                case 1:
                                    txt_name=(TextView)txtbox2.getView();
                                    nameview.setRenderable(txtbox2);
                                    break;
                                case 2:
                                    txt_name=(TextView)txtbox3.getView();
                                    nameview.setRenderable(txtbox3);
                                    break;
                                case 3:
                                    txt_name=(TextView)txtbox4.getView();
                                    nameview.setRenderable(txtbox4);
                                    break;
                                case 4:
                                    txt_name=(TextView)txtbox5.getView();
                                    nameview.setRenderable(txtbox5);
                                    break;
                                case 5:
                                    txt_name=(TextView)txtbox6.getView();
                                    nameview.setRenderable(txtbox6);
                                    break;
                                case 6:
                                    txt_name=(TextView)txtbox7.getView();
                                    nameview.setRenderable(txtbox7);
                                    break;
                                case 7:
                                    txt_name=(TextView)txtbox8.getView();
                                    nameview.setRenderable(txtbox8);
                                    break;
                                case 8:
                                    txt_name=(TextView)txtbox9.getView();
                                    nameview.setRenderable(txtbox9);
                                    break;
                                case 9:
                                    txt_name=(TextView)txtbox10.getView();
                                    nameview.setRenderable(txtbox10);
                                    break;
                            }
                            txt_name.setText(sortingList.get(i).getName());
                            Log.i("yo",sortingList.get(i).getName());
                            nameview.setParent(anchorNode);
                            nameview.setLocalPosition(new Vector3((float) x,1f,(float) -z));
                        }
                    }
                });
    }

    public boolean checkIsSupportedDeviceOrFinish(final Activity activity) {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
            Log.e(TAG,"Sceneform requires N");
            Toast.makeText(activity,"Sceneform requires N",Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }


    public double getDistance(Double lat1,Double lat2,Double lon1,Double lon2)
    {

        lat1=Math.toRadians(lat1);
        lat2=Math.toRadians(lat2);
        lon1=Math.toRadians(lon1);
        lon2=Math.toRadians(lon2);

        double a1=Math.pow(lat1-lat2,2);
        double a2=Math.cos(lat1)*Math.cos(lat2)*Math.pow(lon1-lon2,2);

        final double R=6371500;

        return R*Math.sqrt(a1+a2);
    }


    private void getData() {

        client = LocationServices.getFusedLocationProviderClient(this);

        RequestQueue re = Volley.newRequestQueue(this);


        URL = " http://overpass-api.de/api/interpreter?data=[out:json];(node(12.8133558,80.0323585,12.8333558,80.0523585);%3C;);out%20meta;";



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                response -> {
                    try {
                        JSONArray place = null;
                        place = (JSONArray) response.get("elements");
                        for (int i = 0; i < place.length(); i++) {
                            JSONObject node = (JSONObject) place.get(i);
                            if (node.has("tags") && node.has("lat") && node.has("lon")) {
                                JSONObject temp2 = (JSONObject) node.get("tags");
                                if (temp2.has("name")) {
                                    double d = getDistance(lat, (Double) node.get("lat"), lon, (Double) node.get("lon"));
                                    Result r = new Result(temp2.get("name").toString(),(Double) node.get("lat"),(Double) node.get("lon"),d);
                                    sortingList.add(r);
                                }
                            }
                        }
                        Toast.makeText(this,"data is loaded",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        Log.i("yo",e.getMessage());
                        e.printStackTrace();
                    }
                    Collections.sort(sortingList, (a, b) -> {
                        Double valA = null;
                        Double valB = null;
                        valA =  a.getDis();
                        valB =  b.getDis();
                        if (valA != null) {
                            return valA.compareTo(valB);
                        } else return 1;
                    });

                }, error -> Log.i("error", error.getMessage()));

        re.add(jsonObjectRequest);
    }

    double getAngle(Result a1,Result a2, char x) {
        double a= a1.getDis();
        double b= a2.getDis();
        double c= getDistance(a1.getLat(), a2.getLat(),a1.getLon(),a2.getLon());

        double den;
        double angle = 0;

        if(x=='b')
        {
            den = 2 * a * c;
            a = Math.pow(a, 2);
            b = Math.pow(b, 2);
            c = Math.pow(c, 2);
            angle=(a-b+c)/den;
            Log.i("getRad","angle  "+ angle);

        }
        else if(x=='c')
        {
            den = 2 * a * b;
            a = Math.pow(a, 2);
            b = Math.pow(b, 2);
            c = Math.pow(c, 2);
            angle=(a+b-c)/den;
        }
        else if(x=='a')
        {
            den=2*b*c;
            a = Math.pow(a, 2);
            b = Math.pow(b, 2);
            c = Math.pow(c, 2);
            angle=(-a+b+c)/den;
        }

        angle=Math.toRadians(angle);
        Log.i("getRad","angle final "+ angle);
        return angle;
        // see line comment 266
        // see all comments
        // location abhi manual daali hai
        // api mein bhi values manual hai


    }


}
