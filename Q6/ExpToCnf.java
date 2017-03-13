import java.nio.file.Files;
import java.nio.file.Paths;

public class ExpToCnf{
    public static void main(String[] args){
        System.out.println("Starting program ExpToCnf ----");


        String expFile_Contents = "", cnfFile_Contents = "";
        String expFile_Path, cnfFile_Path;

        expFile_Path = args[0];
        cnfFile_Path = args[1];

        try{
            expFile_Contents = new String(Files.readAllBytes(Paths.get(expFile_Path)));
        }
        catch(Exception e){
            System.out.println("Error opening exp file: " + expFile_Path);
        }

        try{
            cnfFile_Contents = new String(Files.readAllBytes(Paths.get(cnfFile_Path)));
        }
        catch(Exception e){
            System.out.println("Error opening cnf file: " + cnfFile_Path);
        }


       System.out.println(expFile_Contents);
    }
}