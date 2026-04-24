import source.*;
import util.ImageUtils;
import util.Metrics;
import channel.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] dims=new int[2];
        int[] pixels={};
        try{
        dims = new int[2];
        pixels = ImageUtils.readImage("input/sample.jpg", dims);
        }catch(Exception e){
            System.out.println("Error reading the image");
            return;
        }
         String text = ImageUtils.pixelsToString(pixels);

        // Step 1: Huffman Setup
        Map<Character, Integer> freq = HuffmanEncoder.buildFrequency(text);
        Node root = HuffmanEncoder.buildTree(freq);

        Map<Character, String> codes = new HashMap<>();
        HuffmanEncoder.generateCodes(root, "", codes);

        String encoded = HuffmanEncoder.encode(text, codes);

        //System.out.println("Original Text: " + text);
       // System.out.println("Encoded Bits: " + encoded);
        double compression = Metrics.compressionRatio(text, encoded);
        System.out.println("Compression Ratio: " + compression);
        double[] noiseLevels = {0.01, 0.05, 0.1, 0.2};

        for (double noise : noiseLevels) {
            runComparison(encoded, root, noise,dims,compression);
        }
        
    }

    // 🔥 Main comparison function
    private static void runComparison(String encoded, Node root, double noise,int[] dims,double compression) {

        System.out.println("\n==============================");
        System.out.println("Noise Level: " + noise);
        System.out.println("==============================");

        // Case 1: No Coding
        Result noCoding = simulateNoCoding(encoded, root, noise,dims);

        // Case 2: Hamming
        Result hamming = simulateHamming(encoded, root, noise,dims);

        // Case 3: Repetition
        Result repetition = simulateRepetition(encoded, root, noise,dims);

        printComparisonTable(noise, noCoding, hamming, repetition,compression);
    }

    // ✅ No Coding
    private static Result simulateNoCoding(String encoded, Node root, double noise,int[] dims) {
        String noisy = BSCChannel.transmit(encoded, noise);
        String output = HuffmanDecoder.decode(noisy, root);

    //      try {
    //     int[] reconstructedPixels = ImageUtils.stringToPixels(output);

    //     ImageUtils.writeImage(
    //         "output/nocoding_" + noise + ".jpg",
    //         reconstructedPixels,
    //         dims[0],
    //         dims[1]
    //     );
    // } catch (Exception e) {
    //     System.out.println("Error writing Hamming image");
    // }

        double ber = Metrics.calculateBER(encoded, noisy);
        double rate = Metrics.codeRate("NOCODING");
        double acc = Metrics.accuracy(encoded, noisy);

        return new Result(output, ber,rate,acc);
    }

    // ✅ Hamming
    private static Result simulateHamming(String encoded, Node root, double noise,int[] dims) {

        String encodedHamming = Hamming74.encode(encoded);
        String noisy = BSCChannel.transmit(encodedHamming, noise);
        String decodedHamming = Hamming74.decode(noisy);

        decodedHamming = decodedHamming.substring(0, encoded.length());
        String output = HuffmanDecoder.decode(decodedHamming, root);

    //      try {
    //     int[] reconstructedPixels = ImageUtils.stringToPixels(output);

    //     ImageUtils.writeImage(
    //         "output/hamming_" + noise + ".jpg",
    //         reconstructedPixels,
    //         dims[0],
    //         dims[1]
    //     );
    // } catch (Exception e) {
    //     System.out.println("Error writing Hamming image");
    // }

        double ber = Metrics.calculateBER(encoded, decodedHamming);
        double rate = Metrics.codeRate("HAMMING");
        double acc = Metrics.accuracy(encoded, decodedHamming);
        return new Result(output, ber,rate,acc);
    }

    // ✅ Repetition
    private static Result simulateRepetition(String encoded, Node root, double noise,int[] dims) {

        String encodedRep = RepetitionCode.encode(encoded);
        String noisy = BSCChannel.transmit(encodedRep, noise);
        String decodedRep = RepetitionCode.decode(noisy);

        decodedRep = decodedRep.substring(0, encoded.length());
        String output = HuffmanDecoder.decode(decodedRep, root);

    //      try {
    //     int[] reconstructedPixels = ImageUtils.stringToPixels(output);

    //     ImageUtils.writeImage(
    //         "output/repetition_" + noise + ".jpg",
    //         reconstructedPixels,
    //         dims[0],
    //         dims[1]
    //     );
    // } catch (Exception e) {
    //     System.out.println("Error writing Hamming image");
    // }

        double ber = Metrics.calculateBER(encoded, decodedRep);
        double rate = Metrics.codeRate("REPETITION");
         double acc = Metrics.accuracy(encoded, decodedRep);
        
        return new Result(output, ber,rate,acc);
    }

    private static void printComparisonTable(double noise, Result no, Result ham, Result rep, double compression) {

    System.out.println("\n================ PERFORMANCE METRICS ================");
    System.out.println("Noise Level: " + noise + "\n");

    System.out.printf("%-15s %-10s %-12s %-10s %-12s\n",
            "Technique", "BER", "Code Rate", "Accuracy", "Compression");

    System.out.println("-------------------------------------------------------------");

    System.out.printf("%-15s %-10.5f %-12.2f %-10.2f %-12.2f\n",
            "No Coding", no.ber, no.codeRate, no.accuracy, compression);

    System.out.printf("%-15s %-10.5f %-12.2f %-10.2f %-12.2f\n",
            "Hamming", ham.ber, ham.codeRate, ham.accuracy, compression);

    System.out.printf("%-15s %-10.5f %-12.2f %-10.2f %-12.2f\n",
            "Repetition", rep.ber, rep.codeRate, rep.accuracy, compression);

    System.out.println("====================================================");
}

    static class Result {
    String output;
    double ber;
    double codeRate;
    double accuracy;

    Result(String output, double ber, double codeRate, double accuracy) {
        this.output = output;
        this.ber = ber;
        this.codeRate = codeRate;
        this.accuracy = accuracy;
    }
}
}

