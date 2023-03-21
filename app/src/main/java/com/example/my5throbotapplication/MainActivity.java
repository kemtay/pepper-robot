package com.example.my5throbotapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.aldebaran.qi.sdk.object.actuation.PathPlanningPolicy.STRAIGHT_LINES_ONLY;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.TransformBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.actuation.Actuation;
import com.aldebaran.qi.sdk.object.actuation.GoTo;
import com.aldebaran.qi.sdk.builder.GoToBuilder;
import com.aldebaran.qi.sdk.object.actuation.Frame;
import com.aldebaran.qi.sdk.object.actuation.Mapping;
import com.aldebaran.qi.sdk.object.actuation.FreeFrame;
import com.aldebaran.qi.sdk.object.geometry.Transform;
import com.aldebaran.qi.sdk.object.actuation.OrientationPolicy;
import com.aldebaran.qi.sdk.object.actuation.PathPlanningPolicy;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {
    // Store the Animate action.
    private Animate animate;

    // Store the GoTo action.
    private GoTo goTo;
    // Store the Animate action.
    private Animate d_animate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);
    }

    @Override
    protected void onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        // The robot focus is gained.
        // Create a new say action.
        Say say = SayBuilder.with(qiContext) // Create the builder with the context.
                .withText("Hello human! Nice to meet you!") // Set the text to say.
                .build(); // Build the say action.// Create a new say action.

        // Create the second action.
        // Create an animation object.
        Animation myAnimation = AnimationBuilder.with(qiContext)
                .withResources(R.raw.disco_a001)
                .build();
        animate = AnimateBuilder.with(qiContext)
                .withAnimation(myAnimation)
                .build();

        // Execute the action.
        say.run();
        // Add an on started listener to the animate action.
        animate.addOnStartedListener(() -> Log.i(TAG, "Animation started."));
        animate.run();

        // Create an animation.
        Animation animation = AnimationBuilder.with(qiContext) // Create the builder with the context.
                .withResources(R.raw.go_back) // Set the animation resource.
                .build(); // Build the animation.
        // Create an animate action.
        d_animate = AnimateBuilder.with(qiContext) // Create the builder with the context.
                .withAnimation(animation) // Set the animation.
                .build(); // Build the animate action.
        // Add an on started listener to the animate action.
        animate.addOnStartedListener(() -> Log.i(TAG, "Animation started."));
        // Run the action synchronously.
        d_animate.run();


    }

    @Override
    public void onRobotFocusLost() {
        // The robot focus is lost.
        // Remove on started listeners from the GoTo action.
        if (goTo != null) {
            goTo.removeAllOnStartedListeners();
        }

        // Remove on started listeners from the animate action.
        if (animate != null) {
            animate.removeAllOnStartedListeners();
        }

        // Remove on started listeners from the animate action.
        if (d_animate != null) {
            d_animate.removeAllOnStartedListeners();
        }
    }

    @Override
    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
    }
}