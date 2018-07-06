package com.codepath.apps.restclienttemplate.models;

/*
import com.codepath.apps.restclienttemplate.TimelineActivity;
import com.codepath.apps.restclienttemplate.TweetDetailActivity;

// TODO - incorporate gesture listener to add functionality of other gestures

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    protected OnItemClickListener listener;

    private GestureDetector gestureDetector;

    @Nullable
    private View childView;

    private int childViewPosition;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
//        this.gestureDetector = new GestureDetector(context, new GestureListener());
        this.listener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent event) {
        childView = view.findChildViewUnder(event.getX(), event.getY());
        childViewPosition = view.getChildPosition(childView);

        return childView != null && gestureDetector.onTouchEvent(event);
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent event) {
        // Not needed.
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    public interface OnItemClickListener {


         // @param childView View of the item that was clicked.
         // @param position  Position of the item that was clicked.

        void onItemClick(View childView, int position);


        public void onItemLongPress(View childView, int position);

    }


    public static abstract class SimpleOnItemClickListener implements OnItemClickListener {


        //public void onItemClick(View childView, int position) {
          //  Intent i = new Intent(TimelineActivity.this, TweetDetailActivity.class);
            //Tweet result = childView.get(position);
            //i.putExtra("result", result);
            //startActivity(i);
        }


        public void onItemLongPress(View childView, int position) {
            // Do nothing.
        }

    }

    protected class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            if (childView != null) {
                listener.onItemClick(childView, childViewPosition);
            }

            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            if (childView != null) {
                listener.onItemLongPress(childView, childViewPosition);
            }
        }

        @Override
        public boolean onDown(MotionEvent event) {
            // Best practice to always return true here.
            // http://developer.android.com/training/gestures/detector.html#detect
            return true;
        }

    }


}*/