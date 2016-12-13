package edu.buaa.nlp.tw.common;

public class Stopwatch { 

    private long start;

   /**
     * Create a stopwatch object.
     */
    public Stopwatch() {
        start = System.currentTimeMillis();
    } 
    public void reset() {
        start = System.currentTimeMillis();
    } 
   /**
     * Return elapsed time (in seconds) since this object was created.
     */
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

} 
