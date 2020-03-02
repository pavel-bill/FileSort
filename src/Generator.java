import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class Generator {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";

    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;

    private static SecureRandom random = new SecureRandom();


    public static void main(String[] args) {
        int linesCount=Integer.parseInt(args[0]);
        int lineLength=Integer.parseInt(args[1]);
        ArrayList<String> arrayList=new ArrayList<>();
        try(FileWriter writer = new FileWriter("in.txt", false))
        {
            for (int i=0;i<linesCount;i++){
                StringBuilder sb = new StringBuilder(lineLength);
                for (int j = 0; j < lineLength; j++) {
                    int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
                    char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
                    sb.append(rndChar);
                }
                sb.append(System.lineSeparator());
                arrayList.add(sb.toString());
                //writer.write(sb.toString());
            }
            for(String x:arrayList){
                writer.write(x);
            }
            writer.flush();

        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        arrayList.sort(String::compareTo);
        try(FileWriter writer = new FileWriter("test.txt", false))
        {
            for(String x:arrayList){
                writer.write(x);
            }
            writer.flush();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }






    }
}
