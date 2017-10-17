package com.xianrui.hencoder_jike;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    LikeNumberView mLikeNumberView;
    LikeNumberView mLikeNumberView1;
    LikeNumberView mLikeNumberView2;

    ImageView ivLike;
    ImageView ivShining;

    View mLikeBtn;

    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mLikeNumberView = (LikeNumberView) findViewById(R.id.like_number_view);
        mLikeNumberView.setGravity(Gravity.CENTER);
        mLikeNumberView.setTextSize(200);
        mLikeNumberView.setNumber(988);

        mLikeNumberView1 = (LikeNumberView) findViewById(R.id.like_number_view1);
        mLikeNumberView1.setGravity(Gravity.CENTER);
        mLikeNumberView1.setTextSize(200);
        mLikeNumberView1.setNumber(988);

        mLikeNumberView2 = (LikeNumberView) findViewById(R.id.like_number_view2);
        mLikeNumberView2.setGravity(Gravity.CENTER);
        mLikeNumberView2.setTextSize(100);
        mLikeNumberView2.setNumber(123);

        mLikeBtn = findViewById(R.id.like_btn);


        ivLike = (ImageView) findViewById(R.id.iv_like);
        ivShining = (ImageView) findViewById(R.id.iv_shining);

        mLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    mLikeNumberView2.setNumber(mLikeNumberView2.getNumber() + 1, LikeNumberView.Animation.UP);
//                    ivLike.setImageResource(R.mipmap.ic_messages_like_selected);


                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivLike, "scale", 1f, 0.75f);
                    objectAnimator.setDuration(150);
                    objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            ivLike.setScaleX(value);
                            ivLike.setScaleY(value);
                        }
                    });
                    objectAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ivLike.setImageResource(R.mipmap.ic_messages_like_selected);
                            ivShining.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(ivLike, "scale", 0.75f, 1f);
                    objectAnimator1.setDuration(150);
                    objectAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            ivLike.setScaleX(value);
                            ivLike.setScaleY(value);
                        }
                    });
                    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(ivShining, "scale", 0f, 1f);
                    objectAnimator2.setDuration(150);
                    objectAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            ivShining.setScaleX(value);
                            ivShining.setScaleY(value);
                        }
                    });

//                    AnimatorSet animatorSet1 = new AnimatorSet();
//                    animatorSet1.playTogether(objectAnimator1, objectAnimator2);


                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            type = 1;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animatorSet.play(objectAnimator);
                    animatorSet.play(objectAnimator1).with(objectAnimator2);
                    animatorSet.start();

                } else {
                    mLikeNumberView2.setNumber(mLikeNumberView2.getNumber() - 1, LikeNumberView.Animation.DOWN);

                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivLike, "scale", 1f, 0.75f);
                    objectAnimator.setDuration(150);
                    objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            ivLike.setScaleX(value);
                            ivLike.setScaleY(value);
                        }
                    });


                    objectAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ivLike.setImageResource(R.mipmap.ic_messages_like_unselected);
                            ivShining.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(ivLike, "scale", 0.75f, 1f);
                    objectAnimator1.setDuration(150);
                    objectAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            ivLike.setScaleX(value);
                            ivLike.setScaleY(value);
                        }
                    });
                    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(ivShining, "alpha", 1f, 0f);
                    objectAnimator2.setDuration(150);


                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.play(objectAnimator).with(objectAnimator2);
                    animatorSet.play(objectAnimator1);
                    animatorSet.start();
                    animatorSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            type = 0;
                            ivShining.setAlpha(1f);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                }


            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLikeNumberView.setNumber(mLikeNumberView.getNumber() + 123, LikeNumberView.Animation.UP);
                mLikeNumberView1.setNumber(mLikeNumberView1.getNumber() + 123, LikeNumberView.Animation.UP);
            }
        });

        findViewById(R.id.sub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLikeNumberView.setNumber(mLikeNumberView.getNumber() - 1, LikeNumberView.Animation.DOWN);
                mLikeNumberView1.setNumber(mLikeNumberView1.getNumber() - 1, LikeNumberView.Animation.DOWN);
            }
        });

    }
}
