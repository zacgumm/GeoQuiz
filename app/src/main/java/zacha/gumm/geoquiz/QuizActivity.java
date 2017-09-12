package zacha.gumm.geoquiz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private TextView mQuestionTextView;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true, false),
            new Question(R.string.question_oceans, true, false),
            new Question(R.string.question_mideast, false, false),
            new Question(R.string.question_africa, false, false),
            new Question(R.string.question_americas, true, false),
            new Question(R.string.question_asia, true, false),
    };

    private int mCurrentIndex = 0;
    private int score = 0;
    private int total = 0;

    private void updateQuestion() {

        checkProgress();

        int question = mQuestionBank[mCurrentIndex].getTextResId();

        mQuestionTextView.setText(question);

    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        boolean questionIsAnswered = mQuestionBank[mCurrentIndex].isQuestionAnswered();

        if(!questionIsAnswered) {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                score = score + 1;
                total = total + 1;
                Log.d(TAG, String.valueOf(score));
                Log.d(TAG, String.valueOf(total));
                mQuestionBank[mCurrentIndex].setQuestionAnswered(true);
            } else {
                messageResId = R.string.incorrect_toast;
                total = total + 1;
                Log.d(TAG, String.valueOf(total));
                mQuestionBank[mCurrentIndex].setQuestionAnswered(true);
            }
            Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }else {
            messageResId = R.string.question_answered;
            Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

    private void checkProgress(){



        if(total >= mQuestionBank.length){

            int percentage = 100 * (score / total);
            String percent = String.valueOf(percentage);
            String message = "Your grade is a " + percent + "%";
            Log.d(TAG, message);
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

            mNextButton = (Button) findViewById(R.id.next_button);


            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    updateQuestion();
                }

            });


        if(mPreviousButton != null){
        mPreviousButton = (Button) findViewById(R.id.previous_button);

        mPreviousButton.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
               updateQuestion();
           }
        });
        }

        mTrueButton = (Button) findViewById(R.id.true_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mQuestionBank[mCurrentIndex].isQuestionAnswered())
                checkAnswer(true);

            }

        });



        mFalseButton = (Button) findViewById(R.id.false_button);

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!mQuestionBank[mCurrentIndex].isQuestionAnswered())
                checkAnswer(false);

            }

        });

        updateQuestion();
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    public static Toast makeText(Context context, String resId, int duration) {
        Toast toast = new Toast(context);
        return toast;
    }

}

