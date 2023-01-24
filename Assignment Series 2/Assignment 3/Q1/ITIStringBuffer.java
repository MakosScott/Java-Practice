public class ITIStringBuffer {
    
    private String firstString;

    public ITIStringBuffer() {
        this.firstString = "";
    }

    public ITIStringBuffer(String firstString){
        this.firstString = firstString;
    }

    public void append(String nextString){
        
        if (nextString.length() == 0) {
            return ;
        }
    }

    public String toString(){
        return firstString;
    }

}
