package offtop.hyperskill;

import java.util.List;

public class Answers {
    private final String url;
    private boolean checked;
    private final int mode;
    private String str;
    private List<String> list;
    private List<String[]> listArr;
    private boolean[][] b;

    public Answers(String url, boolean checked, int mode, String str) {
        this.url = url;
        this.checked = checked;
        this.mode = mode;
        this.str = str;
    }

    public Answers(String url, boolean checked, int mode, List<String> list) {
        this.url = url;
        this.checked = checked;
        this.mode = mode;
        this.list = list;
    }

    public Answers(String url, boolean checked, List<String[]> strArr, int mode) {
        this.url = url;
        this.checked = checked;
        this.mode = mode;
        this.listArr = strArr;
    }

    public Answers(String url, boolean checked, int mode, boolean[][] b) {
        this.url = url;
        this.checked = checked;
        this.mode = mode;
        this.b = b;
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

    public String getStr() {
        return this.str;
    }

    public List<String> getList() {
        return list;
    }

    public List<String[]> getListArr() {
        return listArr;
    }

    public boolean[][] getB() {
        return b;
    }
}
