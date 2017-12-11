package com.example.alam.pegtest;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class PlacePegActivity extends AppCompatActivity {
    //DrawingView drawingView;

    ProgressBar mProgressBar;
    int i;
    CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //drawingView = new DrawingView(this);
        setContentView(R.layout.activity_place_peg);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().replace(R.id.frame_layout, new PutPegFragment()).commit();


    }
}






//    private class MyTouchListener implements View.OnTouchListener
//    {
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event)
//        {
//            ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
//            String[] mimeTypes ={ClipDescription.MIMETYPE_TEXT_PLAIN};
//            ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, item);
//
//            View.DragShadowBuilder shdwbldr = new View.DragShadowBuilder(v);
//
//            v.startDrag(dragData, shdwbldr, v, 0);
//            v.setVisibility(View.INVISIBLE);
//            return true;
//        }
//
//
//    }
//    private class MyDragListener implements View.OnDragListener
//    {
//
//        @Override
//        public boolean onDrag(View v, DragEvent event)
//        {
//            View view = (View) event.getLocalState();
//            switch (event.getAction()) {
//                case DragEvent.ACTION_DRAG_STARTED:
//                    lParams =(LinearLayout.LayoutParams)view.getLayoutParams();
//                    break;
//                case DragEvent.ACTION_DRAG_ENTERED:
//
//                    int x_cord = (int) event.getX();
//                    int y_cord = (int) event.getY();
//
//                    break;
//                case DragEvent.ACTION_DRAG_EXITED:
//
//                    x_cord = (int) event.getX();
//                    y_cord = (int) event.getY();
//                    lParams.leftMargin = x_cord;
//                    lParams.topMargin = y_cord;
//
//                    view.setLayoutParams(lParams);
//
//                    break;
//                case DragEvent.ACTION_DROP:
//                    // Dropped, reassign View to ViewGroup
//
//                    ViewGroup owner = (ViewGroup) view.getParent();
//                    owner.removeView(view);
//                    LinearLayout container = (LinearLayout) v;
//                    container.addView(view);
//                    view.setVisibility(View.VISIBLE);
//                    view.setX(event.getX());
//                    view.setY(event.getY());
//                    break;
//                case DragEvent.ACTION_DRAG_ENDED:
//
//                    break;
//                default: break;
//
//            }
//
//            return true;
//        }
//    }


//
//    public class DrawingView extends View {
//        protected Bitmap mBitmap;
//        protected Canvas mCanvas, overCanvas;
//        protected Paint mPaint;
//        protected Path mPath;
//
//        public DrawingView(Context context) {
//            super(context);
//            init();
//
//        }
//        @Override
//        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//            super.onSizeChanged(w, h, oldw, oldh);
//            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//            mCanvas = new Canvas(mBitmap);
//        }
//
//        public void init(){
//            mPaint = new Paint(Paint.DITHER_FLAG);
//            mPaint.setAntiAlias(true);
//            mPaint.setDither(true);
//            mPaint.setColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));
//            mPaint.setStyle(Paint.Style.FILL);
//            mPaint.setStrokeJoin(Paint.Join.ROUND);
//            mPaint.setStrokeCap(Paint.Cap.ROUND);
//            mPaint.setStrokeWidth(5);
//
//        }
//        @Override
//        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
//            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
//            canvas.drawCircle(200,200,80,mPaint);
//
//        }
//    }


