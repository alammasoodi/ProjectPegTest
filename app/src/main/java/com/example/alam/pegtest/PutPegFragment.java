package com.example.alam.pegtest;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by alam on 9/12/17.
 */

public class PutPegFragment extends Fragment {
    View v;
    CountDownTimer mCountDownTimer;
    Button dragButton,holeButton;
    LinearLayout hole;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean firstDrag = false;
    int total,failure,success;
    TextView text;
    long initialDropTime = 0;
    long lastDropTime = 0;
    long timeTaken;
    LinearLayout.LayoutParams lParams;
    private static final String Bt_TAG = " BT_DRAG";
    private static final String Hl_TAG = " HL_DRAG";
    int droppedPegs = 0;
    Animation animFadein1,animFadein2,animFadein3;
    LinearLayout draggable_layout;
    ImageView arrow1,arrow2,arrow3;
    //List<FingerTouch> fingers = new LinkedList();
    float mx,my;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_put_peg, container, false);
        pref = getActivity().getApplication().getSharedPreferences("PegPref", 0); // 0 - for private mode
        editor = pref.edit();
//        fingers.add(0, null);
//        fingers.add(1, null);
        dragButton = (Button)v. findViewById(R.id.drag_button);
        holeButton = (Button) v.findViewById(R.id.hole_button);
        arrow1 = (ImageView) v.findViewById(R.id.guide1);
        arrow2 = (ImageView) v.findViewById(R.id.guide2);
        arrow3 = (ImageView) v.findViewById(R.id.guide3);
        arrow1.setColorFilter(getActivity().getResources().getColor(R.color.blue_dark));
        arrow2.setColorFilter(getActivity().getResources().getColor(R.color.blue_dark));
        arrow3.setColorFilter(getActivity().getResources().getColor(R.color.blue_dark));
        animFadein1 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.blink);
        animFadein2 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.blink);
        animFadein3 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.blink);
//        ObjectAnimator animPic1 = ObjectAnimator.ofFloat(guide1, "alpha", 0f, 1f);
//        ObjectAnimator animPic2 = ObjectAnimator.ofFloat(guide2, "alpha", 0f, 1f);
//        ObjectAnimator animPic3 = ObjectAnimator.ofFloat(guide3, "alpha", 0f, 1f);
//        AnimatorSet animSet = new AnimatorSet();
//        animSet.playSequentially(animPic1, animPic2,animPic3);
        //animSet.start();
        AnimationSet animSet = new AnimationSet(false);
        arrow1.startAnimation(animFadein1);
        arrow2.startAnimation(animFadein2);
        arrow3.startAnimation(animFadein3);
        //hole = (LinearLayout) findViewById(R.id.hole);
        //dragButton.setTag(Bt_TAG);
        //hole.setTag(Hl_TAG);


        draggable_layout = (LinearLayout) v.findViewById(R.id.draggable_layout);
        draggable_layout.setOnDragListener(new View.OnDragListener() {
            boolean isSuccessfulDrop = false;

            @Override
            public boolean onDrag(View v, DragEvent event) {
                // TODO Auto-generated method stub
                final int action = event.getAction();
                switch (action) {

                    case DragEvent.ACTION_DRAG_STARTED:
                        if(!firstDrag) {
                            initialDropTime = System.currentTimeMillis();
                            firstDrag = true;
                        }
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;

                    case DragEvent.ACTION_DROP: {
                        droppedPegs = droppedPegs + 1;
                        success = success +1;
                        isSuccessfulDrop = true;
                        return (true);
                    }

                    case DragEvent.ACTION_DRAG_ENDED: {

                        final FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        if (droppedPegs < 3) {
                            if (isSuccessfulDrop) {
                                mCountDownTimer = new CountDownTimer(500, 1000) {

                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        dragButton.setVisibility(View.VISIBLE);
                                    }
                                };
                                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                                mCountDownTimer.start();

                            } else {
                                dragButton.setVisibility(View.VISIBLE);

                                Toast.makeText(getActivity(), "try again", Toast.LENGTH_SHORT).show();

                            }
                            isSuccessfulDrop = false;

                            //text.setText("Total Drops: " + total);


                        } else {
                            lastDropTime = System.currentTimeMillis();
                            timeTaken = lastDropTime - initialDropTime;
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert);
                            alertDialogBuilder.setTitle("Time taken to put all pegs in the hole");
                            alertDialogBuilder.setMessage(String.valueOf(timeTaken / 1000) + " seconds");

                            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    fragmentManager.beginTransaction().replace(R.id.frame_layout, new RemovePegFragment()).commit();


                                }
                            });
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.show().getWindow();

                           // editor.putLong("initialTime",0);

                        }
                    }

                    return (true);
                    default:
                        break;

                }
                return true;
            }
        });
        dragButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();

//                mx = motionEvent.getX();
//                my = motionEvent.getY();
//                int pointerCount = motionEvent.getPointerCount();
//
//                if(pointerCount > 2){
//                    pointerCount = 2;
//                    System.out.println("too many fingers!");
//                } // since i want to handle only two fingers, every other finger will be ignored.
//
//                for (int i = 0; i < pointerCount; i++) {
//
//                    float x = motionEvent.getX(i);
//                    float y = motionEvent.getY(i);
//
//                    int id = motionEvent.getPointerId(i);
//                    int action = motionEvent.getActionMasked();
//                    int actionIndex = motionEvent.getActionIndex();
//
//                    if (action == MotionEvent.ACTION_DOWN
//                            || action == MotionEvent.ACTION_POINTER_DOWN) {
//
//                        if (    fingers.get(i) == null)
//                            fingers.add(i, new FingerTouch(x, y, id));
//
//
//                    }
//
//                    if (fingers.get(i).type == FingerTouch.SCREEN_FINGER) {
//
//                        switch (action) {
//                            case MotionEvent.ACTION_UP:
//                                fingers.remove(i);
//                                Toast.makeText(getActivity(), id+" action up)", Toast.LENGTH_SHORT).show();
//                                break;
//                            case MotionEvent.ACTION_POINTER_UP:
//                                fingers.remove(i);
//                                Toast.makeText(getActivity(), id+" pointer up)", Toast.LENGTH_SHORT).show();
//                                break;
//                            case MotionEvent.ACTION_MOVE:
//                                fingers.get(i).setPos(x, y);
//                                Toast.makeText(getActivity(), id + " action_move!, x: "+fingers.get(i).x+", y: "+fingers.get(i).y, Toast.LENGTH_SHORT).show();
//
//                                break;
//                            default:
//
//                        }
//                    }else if (fingers.get(i).type == FingerTouch.DPAD_FINGER) {
//                        switch (action) {
//                            case MotionEvent.ACTION_UP:
//                                fingers.remove(i);
//                                System.out.println(id + " action_up! - dpad");
//                                break;
//                            case MotionEvent.ACTION_POINTER_UP:
//                                fingers.remove(i);
//                                System.out.println(id + " pointer_up! - dpad");
//                                break;
//                            case MotionEvent.ACTION_MOVE:
//                                fingers.get(i).setPos(x, y);
//                                System.out.println(id + " action_move! - dpad, x: "+fingers.get(i).x+", y: "+fingers.get(i).y);
//                                break;
//                            default:
//
//                        }
//                   }
//                }

//                if(motionEvent.getPointerCount()==2){
//                    Toast.makeText(PlacePegActivity.this, " Two Fingers Tapped Once. Yeeeyy :)", Toast.LENGTH_SHORT).show();
//                    ClipData data = ClipData.newPlainText("", "");
//                    View.DragShadowBuilder shadow = new View.DragShadowBuilder(dragButton);
//                    v.startDrag(data, shadow, null, 0);
//                    v.setVisibility(View.INVISIBLE);
//
//                }
                switch(action & MotionEvent.ACTION_MASK)
                {
                    case MotionEvent.ACTION_POINTER_DOWN:
                        Toast.makeText(getActivity(), " Two Fingers Tapped Once. Yeeeyy :)", Toast.LENGTH_SHORT).show();
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadow = new View.DragShadowBuilder(dragButton);
                        v.startDrag(data, shadow, null, 0);
                        v.setVisibility(View.INVISIBLE);
                        // set the mTwoFingersTapped flag to TRUE when we tap with 2 fingers at once
                        //mTwoFingersTapped = true;
                        break;

                }
                // TODO Auto-generated method stub

                return true;
            }
        });

        return v;
    }
    public double getScreenDistance(double x1, double y1, double x2, double y2) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        double xDist = Math.pow(Math.abs(x1 - x2) / dm.xdpi, 2);
        double yDist = Math.pow(Math.abs(y1 - y2) / dm.ydpi, 2);
        return Math.sqrt(xDist + yDist);
    }
}
