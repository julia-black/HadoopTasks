package com.ssu.juliablack.hadoop.inverted;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

class InvertedIdxMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private static int mapNumber = 0;

    @Override
    protected void setup(Context context) {
        mapNumber++;
        System.out.println("Map" + mapNumber + ": Start");
    }

    @Override
    public void map(LongWritable longWritable, Text text, Context context) throws IOException, InterruptedException {
        String[] pieces = text.toString().toLowerCase().split(":");
        if (pieces.length < 2) {
            throw new IOException("Не удалось считать номер текста");
        }
        int num = Integer.parseInt(pieces[0]);
        String[] words = pieces[1].split(" ");
        for (String word : words) {
            if (word.length() > 0) {
                context.write(new Text(word), new IntWritable(num));
            }
        }
    }

    @Override
    protected void cleanup(Context context) {
        System.out.println("Map" + mapNumber + ": End");
    }
}