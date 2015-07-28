package com.masum.recyclerviewexample;

/*
    STEPS TO HANDLE THE RECYCLER CLICK

    1 Create a class that EXTENDS RecylcerView.OnItemTouchListener

    2 Create an interface inside that class that supports click and long click and indicates the View that was clicked and the position where it was clicked

    3 Create a GestureDetector to detect ACTION_UP single tap and Long Press events

    4 Return true from the singleTap to indicate your GestureDetector has consumed the event.

    5 Find the childView containing the coordinates specified by the MotionEvent and if the childView is not null and the listener is not null either, fire a long click event

    6 Use the onInterceptTouchEvent of your RecyclerView to check if the childView is not null, the listener is not null and the gesture detector consumed the touch event

    7 if above condition holds true, fire the click event

    8 return false from the onInterceptedTouchEvent to give a chance to the childViews of the RecyclerView to process touch events if any.

    9 Add the onItemTouchListener object for our RecyclerView that uses our class created in step 1
     */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "recycler";

    RecyclerView recyclerView;
    InformationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new InformationAdapter(this, getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // setting this event need lot of knowledge about touch framework. I'll make another exmaple for it.
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new MyClickListener() {
            @Override
            public void onClick(View view, int position, Context context) {
                Toast.makeText(context, "onClick position: "+position, Toast.LENGTH_LONG ).show();
            }

            @Override
            public void onLongClick(View view, int position, Context context) {
                Toast.makeText(context, "onLongClick position: "+position, Toast.LENGTH_LONG ).show();
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static List<Information> getData() {
        List<Information> data = new ArrayList<>();
        int[] iconId = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four};
        String[] title = {"facebook", "linkedIn", "twetter", "youtube"};

        for (int i = 0; i < 100; i++) {

            Information currentInfo = new Information();
            currentInfo.iconId = iconId[i % iconId.length];
            currentInfo.title = title[i % title.length];
            data.add(currentInfo);
        }
        return data;
    }

    class RecyclerTouchListener implements  RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        //private Context context;

        public RecyclerTouchListener(final Context context, final RecyclerView recyclerView, final MyClickListener clickListener){
            //this.context = context;
            gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                  //  Log.i(MainActivity.TAG, "onSingleTapUp");

                    int a = e.getAction();
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if(child != null && clickListener!= null){

                        clickListener.onClick(child, recyclerView.getChildPosition(child), context);
                    }

                    return true; //super.onSingleTapUp(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                   // Log.i(MainActivity.TAG, "onLongPress");

                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if(child != null && clickListener!= null){

                        clickListener.onLongClick(child, recyclerView.getChildPosition(child), context);
                    }

                    super.onLongPress(e);
                }
            });

            Log.i(TAG, "RecyclerTouchListener constructor called");

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            // this call will allow GestureDetector.SimpleOnGestureListener to invoke
            gestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.i(MainActivity.TAG, "# onTouchEvent "+e);
        }

    }

    public static interface MyClickListener{
        public void onClick(View view, int position, Context context);
        public void onLongClick(View view, int position, Context context);
    }

}
