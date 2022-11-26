package com.example.php_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText e1;
    Button b1;
    String s;
    String result;
    //String resultnew;
    ListView hotel;//listView的新名稱，要跟在activity_main裡的listview結合
    TextView testtextview;

    ArrayList<String> hotelnamearray;
    ArrayList<String> arysport;//要存放飯店名字的陣列
    ArrayList<String> aryengsport;////要存放飯店價格的陣列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText) findViewById(R.id.et1);
        b1 = (Button) findViewById(R.id.save);
        textView = (TextView) findViewById(R.id.textView);
        testtextview = (TextView) findViewById(R.id.testtextView);
        hotel = (ListView)findViewById(R.id.lsvsports);//要跟在activity_main裡的listview結合
        hotelnamearray = new ArrayList<>();

        arysport=new ArrayList<>();//要存放飯店名字的陣列
        aryengsport=new ArrayList<>();//要存放飯店價格的陣列

        String strjson = "[{\"cname\":\"籃球\",\"ename\":\"basketball\"},{\"cname\":\"足球\",\"ename\":\"soccer\"},{\"cname\":\"排球\",\"ename\":\"volleyball\"}]";



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        s = e1.getText().toString();
                        String url = "http://140.136.151.144/JSONTEST.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                result =response.trim();
                                Toast.makeText(MainActivity.this, response.trim(), Toast.LENGTH_SHORT).show();
                                textView.setText(result);//*********已成功改寫成功哭爛!!!!!!!!!


                                try{
                                    JSONArray array = new JSONArray(result);
                                    for(int i  =0 ; i < array.length() ; i++){
                                        JSONObject jsonObject = array.getJSONObject(i);

                                        String strcname = jsonObject.getString("name");
                                        String strename = jsonObject.getString("price");
                                        arysport.add(strcname);
                                        aryengsport.add(strename);

                                    }
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }

                               listlayoutadapter adasports=new listlayoutadapter(MainActivity.this);

                                // 設定 ListView 的資料來源
                                hotel.setAdapter(adasports);



                            }
                        },new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
                                    }


                        }){
                            protected Map<String, String> getParams() {
                                Map<String,String> params = new HashMap<String,String>();
                                params.put("data",s);

                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                        requestQueue.add(stringRequest);



                        /*Thread thread = new Thread(mutiThread);
                        thread.start(); // 開始執行
                        */
                        //textView.setText(resultnew);



                   /* try{
                        JSONArray array = new JSONArray(result);
                        for(int i  =0 ; i < array.length() ; i++){
                            JSONObject jsonObject = array.getJSONObject(i);

                            String strcname = jsonObject.getString("name");
                            //String strename = jsonObject.getString("ename");
                            arysport.add(strcname);
                            //aryengsport.add(strename);

                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }

                        listlayoutadapter adasports=new listlayoutadapter(MainActivity.this);

                        // 設定 ListView 的資料來源
                        hotel.setAdapter(adasports);*/

                testtextview.setText(result);
            }







        });






   }

    public class listlayoutadapter extends BaseAdapter {

        private LayoutInflater listlayoutInflater;

        public listlayoutadapter(Context c){
            listlayoutInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            //取得ArrayList的總數 (要注意，跟array不同之處)
            return arysport.size();
        }

        @Override
        public Object getItem(int position) {
            //要用get(position)取得資料 (要注意，跟array不同之處)
            return  arysport.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = listlayoutInflater.inflate(R.layout.listlayout,null);

            //設定自訂樣板上物件對應的資料。
            //ImageView img_logo = (ImageView) convertView.findViewById(R.id.imglogo);
            TextView lbl_name = (TextView) convertView.findViewById(R.id.lblname);//在listlayout裡的textview設定名字的
            TextView lbl_engname = (TextView) convertView.findViewById(R.id.lblengname);

            //要用get(position)取得資料 (要注意，跟array不同之處)
            //img_logo.setImageResource(aryimas.get(position));
            lbl_name.setText(arysport.get(position));
            lbl_engname.setText(aryengsport.get(position));

            return convertView;
        }
    }





}