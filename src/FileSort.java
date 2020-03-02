import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class FileSort {
    public static int fileCount = 0;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int linesCount = 0;
        int totalLinesCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("in.txt"))) {
            String line;
            ArrayList<String> arrayList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                line = line + System.lineSeparator();
                arrayList.add(line);
                totalLinesCount++;
                linesCount++;
                if (linesCount >= 10000) {
                    arrayList.sort(String::compareTo);
                    saveArrayToFile(arrayList);
                    linesCount = 0;
                    arrayList.clear();
                }
            }
            if (arrayList.size() > 0) {
                arrayList.sort(String::compareTo);
                saveArrayToFile(arrayList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("total input lines: "+totalLinesCount);
        MergeFiles();

        long stop = System.currentTimeMillis();
        double time=((double)stop-(double)start)/1000;
        System.out.println("total time: "+time+" s");


    }

    public static void saveArrayToFile(ArrayList<String> arrayList) {
        String fileName = "tmp" + Integer.toString(fileCount) + ".txt";
        try (FileWriter fw = new FileWriter(fileName)) {
            for (String x : arrayList) {
                fw.write(x);
            }
            fw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileCount++;
    }

    public static void MergeFiles() {
        ArrayList<BufferedReader> bufferedReaders = new ArrayList<>();
        int totalOutLines=0;
        long startsort = System.currentTimeMillis();
        try {
            FileWriter fw = new FileWriter("out.txt");
            for (int i = 0; i < fileCount; i++) {
                String fileName = "tmp" + Integer.toString(i) + ".txt";
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                bufferedReaders.add(reader);
            }

            ArrayList<Line> linesList = new ArrayList<>();
            for (int i = 0; i < fileCount; i++) {
                String str = "";
                if ((str = bufferedReaders.get(i).readLine()) != null) {
                    Line line = new Line();
                    line.text = str;
                    line.fileNumber = i;
                    linesList.add(line);
                }
            }

            while (linesList.size() > 0) {
                linesList.sort(Line::compareTo);
                int fileToRead = linesList.get(0).fileNumber;
                String lineToWrite = linesList.get(0).text + System.lineSeparator();
                fw.write(lineToWrite);
                totalOutLines++;
                linesList.remove(0);
                String str = "";
                if ((str = bufferedReaders.get(fileToRead).readLine()) != null) {
                    Line line = new Line();
                    line.text = str;
                    line.fileNumber = fileToRead;
                    linesList.add(line);
                }

            }
            fw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long stopsort = System.currentTimeMillis();
        double sorttime=((double)stopsort-(double)startsort)/1000;
        System.out.println("sort time (Array.sort): "+sorttime);

        bufferedReaders.clear();

        startsort = System.currentTimeMillis();

        try {
            FileWriter fw = new FileWriter("out2.txt");
            for (int i = 0; i < fileCount; i++) {
                String fileName = "tmp" + Integer.toString(i) + ".txt";
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                bufferedReaders.add(reader);
            }

            PriorityQueue<Line> priorityQueue=new PriorityQueue<>();

            for (int i = 0; i < fileCount; i++) {
                String str = "";
                if ((str = bufferedReaders.get(i).readLine()) != null) {
                    Line line = new Line();
                    line.text = str;
                    line.fileNumber = i;
                    priorityQueue.add(line);
                }
            }

            while (priorityQueue.size() > 0) {
                int fileToRead = priorityQueue.peek().fileNumber;
                String lineToWrite = priorityQueue.poll().text + System.lineSeparator();
                fw.write(lineToWrite);
                totalOutLines++;
                String str = "";
                if ((str = bufferedReaders.get(fileToRead).readLine()) != null) {
                    Line line = new Line();
                    line.text = str;
                    line.fileNumber = fileToRead;
                    priorityQueue.add(line);
                }

            }
            fw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        stopsort = System.currentTimeMillis();
        sorttime=((double)stopsort-(double)startsort)/1000;
        System.out.println("sort time (Priority queue): "+sorttime);








        System.out.println("total output lines: "+totalOutLines);
    }
}
