package offtop.hyperskill_manager;

import java.util.List;

public class Answer {
    private final String url;
    private boolean checked;
    private final int mode;
    private String answerStr;
    private String[] answerArr;
    private List<String[]> answerListArr;
    private boolean[][] answerBoolean;

    public Answer(String url, boolean checked, int mode, String answerStr) {
        this.url = url;
        this.checked = checked;
        this.mode = mode;
        this.answerStr = answerStr;
    }

    public Answer(String url, boolean checked, int mode, String[] answerArr) {
        this.url = url;
        this.checked = checked;
        this.mode = mode;
        this.answerArr = answerArr;
    }

    public Answer(String url, boolean checked, int mode, List<String[]> answerListArr) {
        this.url = url;
        this.checked = checked;
        this.mode = mode;
        this.answerListArr = answerListArr;
    }

    public Answer(String url, boolean checked, int mode, boolean[][] answerBoolean) {
        this.url = url;
        this.checked = checked;
        this.mode = mode;
        this.answerBoolean = answerBoolean;
    }

    public String getUrl() {
        return this.url;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public int getMode() {
        return this.mode;
    }

    public String getAnswerStr() {
        return this.answerStr;
    }

    public String[] getAnswerArr() {
        return answerArr;
    }

    public List<String[]> getAnswerListArr() {
        return answerListArr;
    }

    public boolean[][] getAnswerBoolean() {
        return answerBoolean;
    }
}
