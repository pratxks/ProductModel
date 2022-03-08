import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.*;

public class ProductReader
{
    static ArrayList<String> ProductRecordArray = new ArrayList<String>();

    public static void ReadFileDataToArrayList()
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";

        try
        {
            // uses a fixed known path:
            //  Path file = Paths.get("c:\\My Documents\\data.txt");

            // use the toolkit to get the current working directory of the IDE
            // Not sure if the toolkit is thread safe...
            File workingDirectory = new File(System.getProperty("user.dir"));

            // Typiacally, we want the user to pick the file so we use a file chooser
            // kind of ugly code to make the chooser work with NIO.
            // Because the chooser is part of Swing it should be thread safe.
            chooser.setCurrentDirectory(workingDirectory);
            // Using the chooser adds some complexity to the code.
            // we have to code the complete program within the conditional return of
            // the filechooser because the user can close it without picking a file

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                // Typical java pattern of inherited classes
                // we wrap a BufferedWriter around a lower level BufferedOutputStream
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                // Finally we can read the file LOL!
                int line = 0;
                while(reader.ready())
                {
                    rec = reader.readLine();
                    ProductRecordArray.add(rec);
                    line++;
                    // echo to screen
                    System.out.printf("\nLine %4d %-60s ", line, rec);
                }
                reader.close(); // must close the file to seal it and flush buffer
                System.out.println("\n\nData file read!");
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void DisplayProductRecords()
    {
        //ID#           Name     Decription      Cost
        String strHeader = String.format("%-11s","ID#");
        strHeader += String.format("%-30s", "Product Name");
        strHeader += String.format("%-50s", "Product Description");
        strHeader += String.format("%-10s", "Product Cost");

        System.out.println(strHeader);
        String strUnderLine = String.format("%101s", " ").replace(' ', '=');
        System.out.println(strUnderLine);

        for(String rec : ProductRecordArray)
        {
            String [] arrRecordFields = rec.split(",");
            String strDisplayRec = "";

            for(int i=0; i<arrRecordFields.length; i++)
            {
                String strRecField = arrRecordFields[i];

                if(i == 0)
                {
                    strRecField = String.format("%8s", strRecField).replace(' ', '0');
                    strRecField += String.format("%2s", " ");
                }
                if(i == 1)
                {
                    strRecField = String.format("%-30s", strRecField);
                }
                if(i == 2)
                {
                    strRecField = String.format("%-50s", strRecField);
                }
                if(i == 3)
                {
                    strRecField = String.format("%-10s", strRecField);
                }

                strDisplayRec += strRecField;
            }

            System.out.println(strDisplayRec);
        }
    }

    public static void main(String[] args)
    {
        ReadFileDataToArrayList();

        DisplayProductRecords();
    }
}
