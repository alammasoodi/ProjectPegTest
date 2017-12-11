package com.example.alam.pegtest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
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
    boolean isFlag;
    int total,failure,success;
    TextView text;
    LinearLayout.LayoutParams lParams;
    private static final String Bt_TAG = " BT_DRAG";
    private static final String Hl_TAG = " HL_DRAG";
    int droppedPegs = 0;
    Animation animFadein;
    LinearLayout draggable_layout;
    ImageView guide1,guide2,guide3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_put_peg, container, false);
        pref = getActivity().getApplication().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        isFlag = pref.getBoolean("flag",false);
        if(isFlag)
            droppedPegs = pref.getInt("droppedPegs",droppedPegs);
        dragButton = (Button)v. findViewById(R.id.drag_button);
        holeButton = (Button) v.findViewById(R.id.hole_button);
        guide1 = (ImageView) v.findViewById(R.id.guide1);
        guide2 = (ImageView) v.findViewById(R.id.guide2);
        guide3 = (ImageView) v.findViewById(R.id.guide3);
        guide1.setColorFilter(getActivity().getResources().getColor(R.color.blue_dark));
        guide2.setColorFilter(getActivity().getResources().getColor(R.color.blue_dark));
        guide3.setColorFilter(getActivity().getResources().getColor(R.color.blue_dark));
        animFadein = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.blink);
//        ObjectAnimator animPic1 = ObjectAnimator.ofFloat(guide1, "alpha", 0f, 1f);
//        ObjectAnimator animPic2 = ObjectAnimator.ofFloat(guide2, "alpha", 0f, 1f);
//        ObjectAnimator animPic3 = ObjectAnimator.ofFloat(guide3, "alpha", 0f, 1f);
//        AnimatorSet animSet = new AnimatorSet();
//        animSet.playSequentially(animPic1, animPic2,animPic3);
        //animSet.start();
        guide1.startAnimation(animFadein);
        guide2.startAnimation(animFadein);
        guide3.startAnimation(animFadein);
        //hole = (LinearLayout) findViewById(R.id.hole);
        //dragButton.setTag(Bt_TAG);
        //hole.setTag(Hl_TAG);
        draggable_layout = (LinearLayout) v.findViewById(R.id.draggable_layout);
        draggable_layout.setOnDragListener(new View.OnDragListener() {
            boolean flag = false;

            @Override
            public boolean onDrag(View v, DragEvent event) {
                // TODO Auto-generated method stub
                final int action = event.getAction();
                switch (action) {

                    case DragEvent.ACTION_DRAG_STARTED:
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;

                    case DragEvent.ACTION_DROP: {
                        droppedPegs = droppedPegs + 1;
                        editor.putInt("droppedPegs",droppedPegs);
                        editor.putBoolean("flag", true);
                        editor.commit();
                        success = success +1;
                        flag = true;
                        return (true);
                    }

                    case DragEvent.ACTION_DRAG_ENDED: {
                        final FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        if (droppedPegs < 2) {
                            if (flag) {
                                mCountDownTimer = new CountDownTimer(500, 1000) {

                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        fragmentManager.beginTransaction().replace(R.id.frame_layout, new PutPegFragment()).commit();

                                    }
                                };
                                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                                mCountDownTimer.start();

                            } else {
                                fragmentManager.beginTransaction().replace(R.id.frame_layout, new PutPegFragment()).commit();

                                Toast.makeText(getActivity(), "try again", Toast.LENGTH_SHORT).show();

                            }

                            //text.setText("Total Drops: " + total);


                        }
                        else {
                            editor.putBoolean("flag", false);
                            editor.putInt("droppedPegs",0);

                            fragmentManager.beginTransaction().replace(R.id.frame_layout, new RemovePegFragment()).commit();

                        }
                    }

                    editor.commit();
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

                return false;
            }
        });

        return v;
    }
}
