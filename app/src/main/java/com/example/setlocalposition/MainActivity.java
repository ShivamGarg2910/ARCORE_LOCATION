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
    ViewRenderable txtbox1;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.activity_main);

        lat =12.8233558;
        lon =80.0423585;
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        getData();

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



        /*try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                throw (new Exception());
            }
            client.getLastLocation().addOnSuccessListener(MainActivity.this, location -> {
                if (location != null) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();

                    Log.i("ankit",lat+" "+lon+ "weqweqwewqeqwweqwe");

                    try {

                        getData();
                        File file = new File(getApplicationContext().getCacheDir(), "POI_dir");

                        FileInputStream inputStream = new FileInputStream(file);

                        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                        final StringBuilder stringBuilder = new StringBuilder();

                        boolean done = false;

                        while (!done) {
                            final String line = reader.readLine();
                            done = (line == null);

                            if (line != null) {
                                stringBuilder.append(line);
                            }
                        }
                        *//*if(stringBuilder.length()==0)
                        {
                            Toast toast =
                                    Toast.makeText(this, "no size", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }*//*
                        Log.i("ankit",stringBuilder.toString());



                        //Log.d("checkdata", "value:"+stringBuilder.toString());
                        reader.close();
                        inputStream.close();


                        JSONObject jsonobj = (JSONObject) new JSONObject(stringBuilder.toString());
                        JSONArray place = (JSONArray) jsonobj.get("elements");

                        for (int i = 0; i < place.length(); i++) {
                            Object temp = place.get(i);
                            JSONObject node = (JSONObject) temp;
                            if (node.get("tags") != null && node.get("lat") != null && node.get("lon") != null) {
                                JSONObject temp2 = (JSONObject) node.get("tags");
                                if (temp2.get("name") != null ) {
                                    double d = getDistance(lat, (Double) node.get("lat"), lon, (Double) node.get("lon"));
                                    node.put("distance", d);
                                    sortingList.add(node);
                                }
                            }
                        }

                        //Thread.sleep(1000);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/


            /*Collections.sort(sortingList, (a, b) -> {
                Double valA = null;
                Double valB = null;
                try {
                    valA = (Double) a.get("distance");
                    valB = (Double) b.get("distance");
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                }

                if (valA != null) {
                    return valA.compareTo(valB);
                } else return 1;

            });*/


        /*for(Object node: sortingList)
        {
            StringBuilder out=new StringBuilder();
            String temp= node.toString();
            out.append("end");
            out.append(temp);
            TextView textView=findViewById(R.id.test_View);
            textView.setText(out.toString());
        }*/



/*        Object node= sortingList.get(0);
        JSONObject temp= (JSONObject) node;
        temp.get("tags");
        StringBuilder str=new StringBuilder();
        str.append("Face towards ");
        str.append(temp.get("name"));
        str.append(" and select a surface");*/






        arFragment.setOnTapArPlaneListener(
                (HitResult hitresult, Plane plane, MotionEvent motionevent) -> {

                    if (lampPostRenderable == null) {
                        return;
                    }

                    Anchor anchor = hitresult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setLocalPosition(Vector3.zero());
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    Node temp2 = new Node();
                    temp2.setLocalPosition(new Vector3(0f , 0.5f, -1.5f ));
                    temp2.setParent(anchorNode);
                    temp2.setRenderable(lampPostRenderable);
        double x;
        double z;
        if(sortingList.size()==0)
        {
            Toast toast = Toast.makeText(this, "no location", Toast.LENGTH_LONG);
            toast.show();
        }else{
            Toast toast = Toast.makeText(this, sortingList.size()+"", Toast.LENGTH_LONG);
            toast.show();
        }
        if(sortingList.size()!=0) {
            for (int i = 0; i < sortingList.size(); i++) {
                double B = getAngle(sortingList.get(0), sortingList.get(i), 'b');
                B = Math.toRadians(B);
                double C = getAngle(sortingList.get(0), sortingList.get(i), 'c');
                C = Math.toRadians(C);
                double c = 0;

                c = getDistance((double) sortingList.get(0).getLat(), (double) sortingList.get(i).getLat(), (double) sortingList.get(0).getLon(),
                        (double) sortingList.get(i).getLon());

                double a = (double) sortingList.get(0).getDis();
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
                x /= 10;
                z /= 10;

                Node temp3 = new Node();
                temp3.setLocalPosition(new Vector3((float) x, 2f, (float) z));
                temp3.setParent(anchorNode);
                temp3.setRenderable(lampPostRenderable);
                TextView txt_name = (TextView) txtbox1.getView();
                txt_name.setText(sortingList.get(i).getLat().toString());
                txt_name.setOnClickListener(v -> anchorNode.setParent(null));


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

        //Log.i("sssss"," lat1 "+ lat1+" lat2 "+lat2+" lon1 "+lon1+" lon2 "+lon2);

        lat1=Math.toRadians(lat1);
        lat2=Math.toRadians(lat2);
        lon1=Math.toRadians(lon1);
        lon2=Math.toRadians(lon2);

        double a1=Math.pow(lat1-lat2,2);
        double a2=Math.cos(lat1)*Math.cos(lat2)*Math.pow(lon1-lon2,2);

        final double R=6371500;

        //Log.i("disss",R*Math.sqrt(a1+a2)+""+" lat1 "+ lat1+" lat2 "+lat2+" lon1 "+lon1+" lon2 "+lon2+" a1 "+a1+" a2 "+a2);

        return R*Math.sqrt(a1+a2);
    }


    private void getData() {

        client = LocationServices.getFusedLocationProviderClient(this);

                /*DiskBasedCache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
                BasicNetwork network = new BasicNetwork(new HurlStack());
                RequestQueue requestQueue = new RequestQueue(cache, network);
                requestQueue.start();*/

                RequestQueue re = Volley.newRequestQueue(this);


                URL = " http://overpass-api.de/api/interpreter?data=[out:json];(node(12.8133558,80.0323585,12.8333558,80.0523585);%3C;);out%20meta;";
               // Log.i("ankit",URL);



                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                        response -> {
                        try {
                                JSONArray place = null;
                                place = (JSONArray) response.get("elements");
                                for (int i = 0; i < place.length(); i++) {
                                    JSONObject node = (JSONObject) place.get(i);
                                    if (node.has("tags") && node.has("lat") && node.has("lon")) {
                                        JSONObject temp2 = (JSONObject) node.get("tags");
                                        //Log.i("yo","3 "+temp2.toString());
                                        if (temp2.has("name")) {
                                            double d = getDistance(lat, (Double) node.get("lat"), lon, (Double) node.get("lon"));
                                            Log.i("thiis",d+"");
                                            Result r = new Result(temp2.get("name").toString(),(Double) node.get("lat"),(Double) node.get("lon"),d);
                                            sortingList.add(r);
                                            Log.i("yo","result "+sortingList.size());
                                        }
                                    }
                                }

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
                            Log.i("ress","heelloo   "+sortingList.get(0).getDis());

                            // Collections.sort(sortingList, );

                        }, error -> Log.i("error", error.getMessage()));

                re.add(jsonObjectRequest);
        //Log.i("done","ankit"+sortingList.get(0));
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

           // Log.i("ffff",angle+"  den "+den+"  a"+a+"     b"+b+"   c"+c);

        }
        else if(x=='c')
        {
            den = 2 * a * b;
            a = Math.pow(a, 2);
            b = Math.pow(b, 2);
            c = Math.pow(c, 2);
            angle=(a+b-c)/den;
            //Log.i("angle",angle+"");
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
       // Log.i("ttd",angle+""+x);
        return angle;


    }


}
