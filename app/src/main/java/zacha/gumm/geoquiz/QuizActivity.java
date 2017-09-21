package zacha.gumm.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static Question currentQuestion;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true, false, false),
            new Question(R.string.question_oceans, true, false, false),
            new Question(R.string.question_mideast, false, false, false),
            new Question(R.string.question_africa, false, false, false),
            new Question(R.string.question_americas, true, false, false),
            new Question(R.string.question_asia, true, false, false),
    };

    private int mCurrentIndex = 0;
    private int score = 0;
    private int total = 0;

    private void updateQuestion() {

        checkProgress();

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        currentQuestion = mQuestionBank[mCurrentIndex];
        mQuestionTextView.setText(question);

        if (!mQuestionBank[mCurrentIndex].isQuestionAnswered()) {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }

        if (mQuestionBank[mCurrentIndex].isQuestionAnswered()) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }


    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        boolean questionIsAnswered = mQuestionBank[mCurrentIndex].isQuestionAnswered();

        if (mQuestionBank[mCurrentIndex].isQuestionCompromised()) {
            messageResId = R.string.judgment_toast;
            mQuestionBank[mCurrentIndex].setQuestionCompromised(true);
        }else {
            if (!questionIsAnswered) {
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
            } else {
                messageResId = R.string.question_answered;
            }
        }

        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    private void checkProgress(){



        if(total >= mQuestionBank.length){

            double percentage = 100 * score / total;
            Log.d(TAG, String.valueOf(percentage));
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



        mPreviousButton = (Button) findViewById(R.id.previous_button);

        mPreviousButton.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
               updateQuestion();
           }
        });


        mTrueButton = (Button) findViewById(R.id.true_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mQuestionBank[mCurrentIndex].isQuestionAnswered())
                checkAnswer(true);
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
            }

        });



        mFalseButton = (Button) findViewById(R.id.false_button);

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!mQuestionBank[mCurrentIndex].isQuestionAnswered())
                checkAnswer(false);
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
            }

        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Starts Cheat Activity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            if (CheatActivity.wasAnswerShown(data)) {
                mQuestionBank[mCurrentIndex].setQuestionCompromised(true);
            }
        }
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


    public static Question getQuestion() {
        return currentQuestion;
    }

}

