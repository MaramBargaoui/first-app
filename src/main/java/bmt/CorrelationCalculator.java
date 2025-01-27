package bmt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CorrelationCalculator {
   public CorrelationCalculator() {
   }

   public static double calculatePearson(List<Double> var0, List<Double> var1) {
      if (var0.size() == var1.size() && var0.size() != 0) {
         double var2 = 0.0;
         double var4 = 0.0;
         double var6 = 0.0;
         double var8 = 0.0;
         double var10 = 0.0;
         int var12 = var0.size();

         for(int var13 = 0; var13 < var12; ++var13) {
            var2 += (Double)var0.get(var13);
            var4 += (Double)var1.get(var13);
            var6 += (Double)var0.get(var13) * (Double)var1.get(var13);
            var8 += Math.pow((Double)var0.get(var13), 2.0);
            var10 += Math.pow((Double)var1.get(var13), 2.0);
         }

         double var17 = (double)var12 * var6 - var2 * var4;
         double var15 = Math.sqrt(((double)var12 * var8 - Math.pow(var2, 2.0)) * ((double)var12 * var10 - Math.pow(var4, 2.0)));
         return var15 == 0.0 ? 0.0 : var17 / var15;
      } else {
         throw new IllegalArgumentException("The lists must have the same non-zero size.");
      }
   }

   public static double calculateSpearman(List<Double> var0, List<Double> var1) {
      if (var0.size() == var1.size() && var0.size() != 0) {
         int var2 = var0.size();
         double var3 = 0.0;
         List var5 = rank(var0);
         List var6 = rank(var1);

         for(int var7 = 0; var7 < var2; ++var7) {
            double var8 = (double)((Integer)var5.get(var7) - (Integer)var6.get(var7));
            var3 += Math.pow(var8, 2.0);
         }

         return 1.0 - 6.0 * var3 / ((double)var2 * (Math.pow((double)var2, 2.0) - 1.0));
      } else {
         throw new IllegalArgumentException("The lists must have the same non-zero size.");
      }
   }

   private static List<Integer> rank(List<Double> var0) {
      ArrayList var1 = new ArrayList();

      for(int var2 = 0; var2 < var0.size(); ++var2) {
         var1.add(var2);
      }

      var1.sort((var1x, var2x) -> {
         return Double.compare((Double)var0.get(var1x), (Double)var0.get(var2x));
      });
      ArrayList var4 = new ArrayList(Collections.nCopies(var0.size(), 0));

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         var4.set((Integer)var1.get(var3), var3 + 1);
      }

      return var4;
   }

   public static Map<String, Double> calculateProfitRevenueRatiosByCountry(Map<String, Double> var0, Map<String, Double> var1, List<String> var2) {
      HashMap var3 = new HashMap();

      double var8;
      for(int var4 = 0; var4 < var2.size(); ++var4) {
         String var5 = (String)var2.get(var4);
         double var6 = (Double)var0.getOrDefault(var5, 0.0);
         var8 = (Double)var1.getOrDefault(var5, 0.0);
         if (var6 > 0.0) {
            double var10 = var8 / var6;
            var3.putIfAbsent(var5, new ArrayList());
            ((List)var3.get(var5)).add(var10);
         }
      }

      HashMap var12 = new HashMap();
      Iterator var13 = var3.keySet().iterator();

      while(var13.hasNext()) {
         String var14 = (String)var13.next();
         List var7 = (List)var3.get(var14);
         var8 = var7.stream().mapToDouble((var0x) -> {
            return var0x;
         }).average().orElse(0.0);
         var12.put(var14, var8);
      }

      return var12;
   }

   public static void main(String[] var0) {
      String var1 = "src/main/java/bmt/imdb_ratings.csv";
      String var2 = "src/main/java/bmt/movies_library.csv";
      String var3 = "src/main/java/bmt/movies_revenues.csv";
      ArrayList var4 = new ArrayList();
      ArrayList var5 = new ArrayList();

      try {
         BufferedReader var6 = new BufferedReader(new FileReader(var1));

         try {
            boolean var8 = true;

            String var7;
            while((var7 = var6.readLine()) != null) {
               if (var8) {
                  var8 = false;
               } else {
                  String[] var9 = var7.split(",");

                  try {
                     var4.add(Double.parseDouble(var9[1]));
                     var5.add(Double.parseDouble(var9[2]));
                  } catch (NumberFormatException var22) {
                     System.out.println("Skipping invalid value.");
                  }
               }
            }
         } catch (Throwable var25) {
            try {
               var6.close();
            } catch (Throwable var21) {
               var25.addSuppressed(var21);
            }

            throw var25;
         }

         var6.close();
      } catch (IOException var26) {
         System.out.println("Error reading the IMDb ratings file: " + var26.getMessage());
      }

      HashMap var27 = new HashMap();
      HashMap var28 = new HashMap();
      ArrayList var29 = new ArrayList();

      double var16;
      try {
         BufferedReader var30 = new BufferedReader(new FileReader(var3));

         try {
            boolean var11 = true;

            String var10;
            while((var10 = var30.readLine()) != null) {
               if (var11) {
                  var11 = false;
               } else {
                  String[] var12 = var10.split(",");
                  String var13 = var12[4];
                  double var14 = Double.parseDouble(var12[2]);
                  var16 = Double.parseDouble(var12[3]);
                  var27.put(var13, var14);
                  var28.put(var13, var16);
                  var29.add(var13);
               }
            }
         } catch (Throwable var23) {
            try {
               var30.close();
            } catch (Throwable var20) {
               var23.addSuppressed(var20);
            }

            throw var23;
         }

         var30.close();
      } catch (IOException var24) {
         System.out.println("Error reading the revenues file: " + var24.getMessage());
      }

      double var31 = calculatePearson(var4, var5);
      double var32 = calculateSpearman(var4, var5);
      System.out.println("Pearson Correlation (Ratings & Revenue): " + var31);
      System.out.println("Spearman Correlation (Ratings & Revenue): " + var32);
      Map var33 = calculateProfitRevenueRatiosByCountry(var27, var28, var29);
      ArrayList var34 = new ArrayList(var33.values());
      ArrayList var15 = new ArrayList();
      Iterator var35 = var29.iterator();

      while(var35.hasNext()) {
         String var17 = (String)var35.next();
         var15.add((double)var29.indexOf(var17));
      }

      var16 = calculatePearson(var15, var34);
      double var18 = calculateSpearman(var15, var34);
      System.out.println("\nProfitability Analysis by Country (Profit/Revenue Ratio)");
      System.out.println("Pearson Correlation (Country & Profit/Revenue Ratio): " + var16);
      System.out.println("Spearman Correlation (Country & Profit/Revenue Ratio): " + var18);
   }
}
