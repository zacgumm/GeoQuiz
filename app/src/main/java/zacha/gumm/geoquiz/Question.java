package zacha.gumm.geoquiz;

/**
 * Created by ZGumm on 9/8/17.
 */
public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean misAnswered;

    public Question(int textResId, boolean answerTrue, boolean isAnswered) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        misAnswered = isAnswered;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResID(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isQuestionAnswered(){
        return misAnswered;
    }

    public void setQuestionAnswered(boolean isAnswered){
        misAnswered = isAnswered;
    }
}



