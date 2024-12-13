package com.example.lab4_bai1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.*;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnFadeInXml, btnFadeOutXml, btnBlinkXml, btnZoomInXml,
            btnZoomOutXml, btnRotateXml, btnMoveXml, btnSlideUpXml,
            btnBounceXml, btnCombineXml, btnFadeInCode, btnFadeOutCode,
            btnZoomInCode, btnZoomOutCode, btnBlinkCode, btnRotateCode,
            btnMoveCode, btnSlideUpCode, btnBounceCode, btnCombineCode;
    private ImageView ivUitLogo;
    private Animation.AnimationListener animationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivUitLogo = findViewById(R.id.iv_uit_logo);
        ivUitLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tải animation từ file XML
                Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_fade_out);

                // Lắng nghe khi hiệu ứng fade-out kết thúc
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Có thể làm gì đó khi animation bắt đầu, nếu cần
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Sau khi fade-out kết thúc, thực hiện chuyển trang
                        Intent intent = new Intent(MainActivity.this, bai4_3.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // Có thể làm gì đó khi animation được lặp lại, nếu cần
                    }
                });

                // Áp dụng hiệu ứng fade-out lên logo
                ivUitLogo.startAnimation(fadeOut);
            }
        });


        // Initialize views
        findViewsByIds();

        // Initialize animation listener
        initVariables();

        // Set animations from XML
        setupButtonAnimations();

        // Set animations from code
        setupCodeBasedAnimations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Thực hiện fade_in khi quay lại Activity
        if (ivUitLogo != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_fade_in);
            ivUitLogo.startAnimation(fadeIn);
        }
    }

    private void findViewsByIds() {
        ivUitLogo = findViewById(R.id.iv_uit_logo);
        btnFadeInXml = findViewById(R.id.btn_fade_in_xml);
        btnFadeOutXml = findViewById(R.id.btn_fade_out_xml);
        btnBlinkXml = findViewById(R.id.btn_blink_xml);
        btnZoomInXml = findViewById(R.id.btn_zoom_in_xml);
        btnZoomOutXml = findViewById(R.id.btn_zoom_out_xml);
        btnRotateXml = findViewById(R.id.btn_rotate_xml);
        btnMoveXml = findViewById(R.id.btn_move_xml);
        btnSlideUpXml = findViewById(R.id.btn_slide_up_xml);
        btnBounceXml = findViewById(R.id.btn_bounce_xml);
        btnCombineXml = findViewById(R.id.btn_combine_xml);
        btnFadeInCode = findViewById(R.id.btn_fade_in_code);
        btnFadeOutCode = findViewById(R.id.btn_fade_out_code);
        btnZoomInCode = findViewById(R.id.btn_zoom_in_code);
        btnZoomOutCode = findViewById(R.id.btn_zoom_out_code);
        btnBlinkCode = findViewById(R.id.btn_blink_code);
        btnRotateCode = findViewById(R.id.btn_rotate_code);
        btnMoveCode = findViewById(R.id.btn_move_code);
        btnSlideUpCode = findViewById(R.id.btn_slide_up_code);
        btnBounceCode = findViewById(R.id.btn_bounce_code);
        btnCombineCode = findViewById(R.id.btn_combine_code);
    }

    private void initVariables() {
        animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Toast.makeText(getApplicationContext(), "Animation Started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(), "Animation Ended", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Toast.makeText(getApplicationContext(), "Animation Repeated", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void setupButtonAnimations() {
        setupAnimationForButton(btnFadeInXml, R.anim.anim_fade_in);
        setupAnimationForButton(btnFadeOutXml, R.anim.anim_fade_out);
        setupAnimationForButton(btnBlinkXml, R.anim.anim_blink);
        setupAnimationForButton(btnZoomInXml, R.anim.anim_zoom_in);
        setupAnimationForButton(btnZoomOutXml, R.anim.anim_zoom_out);
        setupAnimationForButton(btnRotateXml, R.anim.anim_rotate);
        setupAnimationForButton(btnMoveXml, R.anim.anim_move);
        setupAnimationForButton(btnSlideUpXml, R.anim.anim_slide_up);
        setupAnimationForButton(btnBounceXml, R.anim.anim_bounce);
        setupAnimationForButton(btnCombineXml, R.anim.anim_combine);
    }

    private void setupAnimationForButton(Button button, int animResId) {
        Animation animation = AnimationUtils.loadAnimation(this, animResId);
        animation.setAnimationListener(animationListener);
        button.setOnClickListener(v -> ivUitLogo.startAnimation(animation));
    }

    private void setupCodeBasedAnimations() {
        handleClickAnimationCode(btnFadeInCode, createFadeInAnimation());
        handleClickAnimationCode(btnFadeOutCode, createFadeOutAnimation());
        handleClickAnimationCode(btnZoomInCode, createZoomInAnimation());
        handleClickAnimationCode(btnZoomOutCode, createZoomOutAnimation());
        handleClickAnimationCode(btnBlinkCode, createBlinkAnimation());
        handleClickAnimationCode(btnRotateCode, createRotateAnimation());
        handleClickAnimationCode(btnMoveCode, createMoveAnimation());
        handleClickAnimationCode(btnSlideUpCode, createSlideUpAnimation());
        handleClickAnimationCode(btnBounceCode, createBounceAnimation());
        handleClickAnimationCode(btnCombineCode, createCombineAnimation(this));
    }

    private void handleClickAnimationCode(Button button, final Animation animation) {
        button.setOnClickListener(v -> ivUitLogo.startAnimation(animation));
    }

    private Animation createFadeInAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        return animation;
    }

    private Animation createFadeOutAnimation() {
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        return animation;
    }

    private Animation createBlinkAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(300);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(3);
        return animation;
    }

    private Animation createRotateAnimation() {
        RotateAnimation animation = new RotateAnimation(
                0, 360, // Góc bắt đầu và kết thúc
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(2400); // Thời gian thực hiện (ms)
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(1);
        animation.setInterpolator(new CycleInterpolator(1.0f)); // Chu kỳ nội suy một vòng
        return animation;
    }

    private Animation createMoveAnimation() {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0.75f, // toXDelta
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0f
        );
        animation.setDuration(800);
        animation.setFillAfter(true);
        animation.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.linear_interpolator));
        return animation;
    }


    private Animation createSlideUpAnimation() {
        ScaleAnimation animation = new ScaleAnimation(
                1f, 1f,
                1f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(500);
        animation.setFillAfter(true);
        return animation;
    }

    private Animation createBounceAnimation() {
        ScaleAnimation animation = new ScaleAnimation(
                1.0f, 1.0f,
                0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.bounce_interpolator));
        return animation;
    }

    private Animation createCombineAnimation(Context context) {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1, 3, 1, 3,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(4000);

        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(500);
        rotateAnimation.setRepeatCount(2);
        rotateAnimation.setRepeatMode(Animation.RESTART);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.setInterpolator(AnimationUtils.loadInterpolator(context, android.R.anim.linear_interpolator));

        return animationSet;
    }

    private Animation createZoomInAnimation() {
        ScaleAnimation animation = new ScaleAnimation(
                1f, 3f, 1f, 3f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }

    private Animation createZoomOutAnimation() {
        ScaleAnimation animation = new ScaleAnimation(
                1.0f, 0.5f,
                1.0f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }
}
