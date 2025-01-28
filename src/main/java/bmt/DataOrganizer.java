package bmt;

// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class DataOrganizer {
   public DataOrganizer() {
   }


   public static void main(String[] var0) {
      List var1 = Arrays.asList(new Movie(1, "Romance", 2018, 6.5), new Movie(2, "Romance", 2019, 6.3), new Movie(3, "Romance", 2020, 6.25), new Movie(4, "Action", 2018, 7.8), new Movie(5, "Action", 2019, 7.5), new Movie(6, "Action", 2020, 7.9));
      Map var2 = organizeData(var1);
      Iterator var3 = var2.entrySet().iterator();


      while(var3.hasNext()) {
         Map.Entry var4 = (Map.Entry)var3.next();
         System.out.println("Genre: " + (String)var4.getKey());
         Iterator var5 = ((Map)var4.getValue()).entrySet().iterator();


         while(var5.hasNext()) {
            Map.Entry var6 = (Map.Entry)var5.next();
            double var7 = calculateAverage((List)var6.getValue());
            PrintStream var10000 = System.out;
            String var10001 = String.valueOf(var6.getKey());
            var10000.println("  Year: " + var10001 + ", Average Rating: " + var7);
         }
      }


   }


   public static Map<String, Map<Integer, List<Double>>> organizeData(List<Movie> var0) {
      HashMap var1 = new HashMap();
      Iterator var2 = var0.iterator();


      while(var2.hasNext()) {
         Movie var3 = (Movie)var2.next();
         String var4 = var3.getGenres();
         int var5 = var3.getReleaseYear();
         double var6 = var3.getAverageRating();
         var1.putIfAbsent(var4, new HashMap());
         ((Map)var1.get(var4)).putIfAbsent(var5, new ArrayList());
         ((List)((Map)var1.get(var4)).get(var5)).add(var6);
      }


      return var1;
   }


   public static double calculateAverage(List<Double> var0) {
      double var1 = 0.0;


      double var4;
      for(Iterator var3 = var0.iterator(); var3.hasNext(); var1 += var4) {
         var4 = (Double)var3.next();
      }


      return var1 / (double)var0.size();
   }
}



