public class Mark {
    public String firstChar;
    public String specialChar;
    public String lastChar;
    public Integer handling;
    public String firstHtml;
    public String specialHtml;
    public String lastHtml;

    public Mark (String firstChar, String specialChar, String lastChar, Integer handling, String firstHtml, String specialHtml, String lastHtml){
        this.firstChar = firstChar;
        this.specialChar = specialChar;
        this.lastChar = lastChar;
        this.handling = handling;
        this.firstHtml = firstHtml;
        this.specialHtml = specialHtml;
        this.lastHtml = lastHtml;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "firstChar='" + firstChar + '\'' +
                ", specialChar='" + specialChar + '\'' +
                ", lastChar='" + lastChar + '\'' +
                ", handling=" + handling +
                ", firstHtml='" + firstHtml + '\'' +
                ", specialHtml='" + specialHtml + '\'' +
                ", lastHtml='" + lastHtml + '\'' +
                '}';
    }
}
