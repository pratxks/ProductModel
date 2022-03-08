import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.*;

public class ProductWriter
{
    static ArrayList<String> ProductRecordArray = new ArrayList<String>();

    public static void InputProductData()
    {
        boolean newProduct;

        Scanner inStream = new Scanner(System.in);

        String ID, Name, Description;
        double Cost;

        do
        {
            ID = "";
            Name = "";
            Description = "";
            Cost = 0.0f;
            newProduct = false;

            ID = SafeInput.getNonZeroLenString(inStream, "Enter Product ID (a string)");
            Name = SafeInput.getNonZeroLenString(inStream, "Enter Product Name");
            Description = SafeInput.getNonZeroLenString(inStream, "Enter Product Description");
            Cost = SafeInput.getDouble(inStream, "Enter Product's Cost");

            String ProductRecord = "";
            ProductRecord = String.format("%s, %s, %s, %.1f", ID, Name, Description, Cost);
            System.out.println(ProductRecord);
            ProductRecordArray.add(ProductRecord);

            if(!newProduct)
            {
                newProduct = SafeInput.getYNConfirm(inStream, "\n\nDo you want to input new product details (Y/N): ");
            }

        }while (newProduct);
    }

    public static void WriteProductDataToFile()
    {
        // use the toolkit to get the current working directory of the IDE
        // will create the file within the Netbeans project src folder
        // (may need to adjust for other IDE)
        // Not sure if the toolkit is thread safe...
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "/src/ProductTestData.txt");

        System.out.println(file);
        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!

            for(String rec : ProductRecordArray)
            {
                writer.write(rec, 0, rec.length());  // stupid syntax for write rec
                // 0 is where to start (1st char) the write
                // rec. length() is how many chars to write (all)
                writer.newLine();  // adds the new line
            }

            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        InputProductData();

        for(int i=0; i<ProductRecordArray.size(); i++)
        {
            System.out.println(ProductRecordArray.get(i));
        }

        WriteProductDataToFile();
    }
}
