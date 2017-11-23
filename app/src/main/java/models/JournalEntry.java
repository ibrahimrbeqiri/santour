package models;

/**
 * Created by Emile on 23.11.2017.
 */

//JournalEntry test object
public class JournalEntry {

    public String journalId;
    public String title;
    public String content;

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getJournalId() {
        return journalId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }


}
